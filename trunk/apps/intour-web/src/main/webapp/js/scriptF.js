 var map;
   var server = "http://localhost:9000/intour-web";
   markers_data= [];
   markers_pos= [];
   markers_result =[];
   caracteristicas = [];
   var data = [];
   var aux=0;
   var latOrigen=0;
   var lngOrigen=0;
   	
    
    function loadResults (data) {
      
      var items;
   
      if (data.suggestion.length > 0) {
        items = data.suggestion;
        for (var i = 0; i < items.length; i++) {
          var item = items[i];
          if (item.lat != undefined && item.lon != undefined) {
            var icon = server+'/img/poi.png';//FIXME per type
            var infoContent = "<ul><li>Name:"+item.name+"</li><li>Description:"+ item.description+"</li><li>Lat:"+item.lat+"</li><li>Long:"+item.lon+"</li></ul>";
		    
		    if(item.lat !="" && item.lon !=""){
			   	markers_data.push({
	              lat : item.lat,
	              lng : item.lon,
	              title : item.name,
	              description:item.description,
	              uri: item.uri,
	              infoWindow: {
	        		content : infoContent
			      }
	            });
	            markers_pos.push({
	              lat : item.lat,
	              lng : item.lon});
			    }
 
          }
        }
      }
      	localizarOrigen();
        map.addMarkers(markers_data);
        if(markers_pos.length>=2 && latOrigen!=0 && lngOrigen!=0){
        	calcularRutaREST();
        }
    }

	function localizarOrigen(){
	
		GMaps.geolocate({
		  success: function(position) {
		  	latOrigen=position.coords.latitude;
		  	lngOrigen=position.coords.longitude;
		  	//$('#results').append("latOrigen:"+latOrigen+",lngOrigen:"+lngOrigen);
		  	var infoContent = "<ul><li>Name:Mi posicion actual</li><li>Lat:"+latOrigen+"</li><li>Long:"+lngOrigen+"</li></ul>";
		    map.setCenter(position.coords.latitude, position.coords.longitude);
		    map.addMarker({
			  lat: position.coords.latitude,
			  lng: position.coords.longitude,
			  title: 'Mi posicion',
			  infoWindow: {
        		content : infoContent
		      }
			});
			
		  },
		  error: function(error) {
		    alert('Geolocation failed: '+error.message);
		  },
		  not_supported: function() {
		    alert("Your browser does not support geolocation");
		  },
		});
	
	}

    function printResults(data) {
      $('#intour-results').text(JSON.stringify(data));
      prettyPrint();
    }

    $(document).on('click', '.pan-to-marker', function(e) {
      e.preventDefault();

      var position, lat, lng, $index;

      $index = $(this).data('marker-index');

      position = map.markers[$index].getPosition();

      lat = position.lat();
      lng = position.lng();

      map.setCenter(lat, lng);
    });
    
 
 function eliminarRepetidos(pos){
 	
 	var posicionEliminar=0;
 	var longitud=data.length;
 	for(var i=0; i<markers_pos.length;i++){
 		if(markers_pos[pos].lat==markers_pos[i].lat && markers_pos[pos].lng==markers_pos[i].lng && pos !=i ){
 			posicionEliminar=i;
 			if(posicionEliminar<pos){
 				posicionEliminar=0;
 				return -1;
 			}else{
 				posicionEliminar=0;
 				data[longitud]=markers_pos[pos];
 			}	
 		}
 	}
 	if(posicionEliminar==0){
		data[longitud]=markers_pos[pos]; 	
 	}
 }
 
 function vaciarDatos(){
 	for(var i=0; i<data.length;i++){
 		data[i]="";
 	}
 }
 
 
function calcularRutaREST(){

   //Cargamos los datos para enviarles al servicio de calculo de ruta
   vaciarDatos();
   for(var i=0; i< markers_pos.length;i++){
   		eliminarRepetidos(i);
   		//data[i]= markers_pos[i];
   }
   //Fin
   var markers_request = JSON.stringify(data);
   //var serializedData='{"param1":"hello","param2":"world","markers":'+markers_request+'}';
   var serializedData='{"latOrigen":'+latOrigen+',"lngOrigen":'+lngOrigen+',"markers":'+markers_request+'}';
   var request = $.ajax({
        url: server+'/services/route',
        headers: {          
                 Accept : "application/json; charset=utf-8",         
                "Content-Type": "application/json; charset=utf-8"   
  		},  
        type: "post",
        data: serializedData
    });

    // Callback handler that will be called on success
    request.done(function (response, textStatus, jqXHR){
        console.log(response);
       if (response.camino.length > 0) {
       
        items = response.camino;
	        for (var i = 0; i < items.length; i++) {
	       // $('#results').append("Lat "+i+":"+items[i].lat+",Longitud "+i+":"+items[i].lon);
	        
	        if(i!=items.length-1){
	        	map.drawRoute({
				  origin: [items[i].lat, items[i].lon],
				  destination: [items[i+1].lat, items[i+1].lon],
				  travelMode: 'driving',
				  strokeColor: '#131540',
				  strokeOpacity: 0.6,
				  strokeWeight: 6
				});
	        
	        
	        } 
			
	          var item = items[i];
	          if (item.lat != undefined && item.lon != undefined) {
	            var icon = server+'/img/poi.png';//FIXME per type
	            var infoContent = "<ul><li>Name:"+item.name+"</li><li>Description:"+ item.description+"</li><li>Lat:"+item.lat+"</li><li>Long:"+item.lon+"</li></ul>";
			     markers_result.push({
	              lat : item.lat,
	              lng : item.lon,
	              title : item.name,
	              description:item.description,
	              uri: item.uri,
	              infoWindow: {
	        		content : infoContent
			      }
	            });
	          }
	        }
        
         map.addMarkers(markers_result);
         dibujarRuta();
      }
      
      
    });

    // Callback handler that will be called on failure
    request.fail(function (jqXHR, textStatus, errorThrown){
        // Log the error to the console
        console.error(
            "The following error occurred: "+
            textStatus, errorThrown
        );
    });

}
	function dibujarRuta(){
		
		for(var i=0; i< markers_result.length;i++){
			map.drawRoute({
			  origin: [markers_result[i].lat, markers_result[i].lng],
			  destination: [markers_result[i+1].lat, markers_result[i+1].lng],
			  travelMode: 'driving',
			  strokeColor: '#131540',
			  strokeOpacity: 0.6,
			  strokeWeight: 6
			});
		}
	
	}

	function imprimirRuta(){
		for(var i=0; i< markers_result.length; i++){
			//$('#results').append("Lat "+i+":"+ markers_result[i].lat+", Longitud "+i+":"+ markers_result[i].lng);
		}
	
	}

	 function initMap(){
	  map = new GMaps({
	        div: '#map',
	        lat: 40.4000,
	        lng: 3.7167
	      });
	
	      map.on('marker_added', function (marker) {
	        var index = map.markers.indexOf(marker);
	       // $('#results').append('<li><a href="'+marker.uri+'" class="pan-to-marker" data-marker-index="' + index + '">' + marker.title + '</a></li>');
	
	        if (index == map.markers.length - 1) {
	          map.fitZoom();
	        }
	      });
	 }
 
 // Removes the markers from the map, but keeps them in the array.
	function clearMarkers() {
  		setMapOnAll(null);
	}

    $(document).ready(function(){
    
      prettyPrint();
      initMap();

      var xhr = $.getJSON(server+'/services/search');

      //xhr.done(printResults);
      xhr.done(loadResults);
    });

  function clearName() {
      $('#name').val="";
  }
  
  
  
  function search(input,ID) {
    		
    	var name=$('#'+ID).val()
    	
  		/*switch(input){
  		
  			case 0: name= $('#nombre').val(); break;
  			case 1: name= $('#birthPlace').val(); break;
  			case 2: name= $('#birthDate').val(); break;
  			case 3: name= $('#description').val(); break;
  			case 4: name= $('#nationality').val(); break;
  			case 5: name= $('#field').val(); break;
  			case 6: name= $('#museum').val(); break;
  		}*/
  
         if (name!= null && name != "Novalue") {
            initMap();//FIXME: Check
         	var xhr = $.getJSON(server+'/services/search?namePlace='+name+'&input='+input);
         	//xhr.done(printResults);
         	xhr.done(loadResults);
         }
  }
  
  function clearInputs(){
     
     for(var i=0; i<caracteristicas.length; i++){
        caracteristicas[i]="";
     }
  }
  
  function mostrarInputs(){
     
      var obtenidos= document.getElementById("obtenidos");
      var auxParam;
      var auxId;
      
      for(var i=0; i<caracteristicas.length; i++){
      
      	var boton = document.createElement('input');
      	if(caracteristicas[i].valor!=undefined){
      	
  			boton.type='button';
  			boton.id='boton'+i;
  			boton.className='resultsCaract';
  			boton.value=caracteristicas[i].valor;
  			auxParam=caracteristicas[i].tipo;
  			auxId=boton.id;
      		obtenidos.appendChild(boton);
      		$("#"+auxId).on('click', function() {
  				alert('Tenemos click');
  				//search(auxParam,auxId); 
 			});
      	}
     
     }
     
  }
  
  
  function loadResultsPerson (data) {
      var items;
   	  var valores = document.getElementsByClassName("valores");
    
      if (data.suggestion.length > 0) {
        items = data.suggestion;
        for (var i = 0; i < items.length; i++) {
          var item = items[i];
          var infoContent = "<ul><li>Name:"+item.name+"</li><li>birthPlace:"+ item.birthPlace+"</li><li>birthDate:"+item.birthDate+"</li><li>Description:"+item.description+"</li><li>Nationality:"+item.nationality+"</li><li>Field:"+item.field+"</li><li>Museum:"+item.museum+"</li></ul>";
		  //$('#resultsPerson').append(infoContent);
		  
		  clearInputs();
		  	  if(item.name!=""){
			  
			  	caracteristicas.push({
			  		valor: item.name,
			  		tipo: "0"
			  	});
			  	
			  }
			  if(item.birthPlace!=""){
			  	
			  	caracteristicas.push({
			  		valor: item.birthPlace,
			  		tipo: "1"
			  	});
			  }
			  if(item.birthDate!=""){
			  
			  	caracteristicas.push({
			  		valor: item.birthDate,
			  		tipo: "2"
			  	});
			  }
			  if(item.description!=""){
			  	
			  	caracteristicas.push({
			  		valor: item.description,
			  		tipo: "3"
			  	});
			  }
			  if(item.nationality!=""){
			  	caracteristicas.push({
			  		valor: item.nationality,
			  		tipo: "4"
			  	});
			  }
			  if(item.field!=""){
			  	caracteristicas.push({
			  		valor: item.field,
			  		tipo: "5"
			  	});
			  }
			  if(item.museum!=""){
			  	caracteristicas.push({
			  		valor: item.museum,
			  		tipo: "6"
			  	});
			  }
			  mostrarInputs();
		  
		  
        }
      }
      
      
   }
  
  
  function searchPerson(){
  		
  		var caracteristicas= document.getElementById("Caracteristicas");
  		var ccaracteristicas= document.getElementById("CCaracteristicas");
  		var obtenidos= document.getElementById("obtenidos");
  		var seleccionados= document.getElementById("seleccionados");
  		var footer= document.getElementById("Footer");
  		var buscador = document.getElementById("Buscador");
  		var pruebas= document.getElementById("Pruebas");
  		var contenido = document.getElementById("Contenido");
  		var ccontenido = document.getElementById("CContenido");
  		var name= $('#name').val();
         if (name!= null && name != '') {
         	$('#msg3').text("Buscar por: "+ name); 
         	caracteristicas.style.visibility="visible";
         	caracteristicas.style.marginTop="-31%";
         	caracteristicas.style.marginBottom="6%";
         	caracteristicas.style.marginLeft="-24px";
         	ccaracteristicas.style.paddingBottom="4%";
         	obtenidos.style.backgroundColor="white";
         	obtenidos.style.height="50%";
         	obtenidos.style.marginLeft="5%";
         	obtenidos.style.width="85%";
         	obtenidos.style.padding="2%";
         	obtenidos.style.border="3px solid #A3C835";
         	obtenidos.style.borderRadius = "8px";
         	seleccionados.style.backgroundColor="white";
         	seleccionados.style.height="50%";
         	seleccionados.style.marginLeft="5%";
         	seleccionados.style.width="85%";
         	seleccionados.style.padding="2%";
         	seleccionados.style.border="3px solid #A3C835";
         	seleccionados.style.borderRadius = "8px";
         	footer.style.marginTop="0%";
         	footer.style.paddingTop="0%";
         	footer.style.paddingBottom="6%";
         	contenido.style.backgroundImage="none";
         	ccontenido.style.marginTop="-1%";
         	buscador.style.backgroundColor="#E5E5E5";
         	document.body.style.backgroundColor="white";
         	var xhr = $.getJSON(server+'/services/person?name='+name);
         	xhr.done(loadResultsPerson);
         }
  }
  
  function mostrarBusqueda(){
  
		var buscador = document.getElementById("Buscador");
		var contenido = document.getElementById("Contenido");
		var footer = document.getElementById("Footer");
		var iconos=document.getElementById("Iconos");
		var mapa = document.getElementById("Mapa");
	  	var bienvenida = document.getElementById("Bienvenida");
	  	var auxBoton = document.getElementById("auxBoton");
	  	bienvenida.style.visibility="hidden";
	  	auxBoton.style.visibility="hidden";
	  	buscador.style.visibility="visible";
	  	iconos.style.visibility="hidden";
	  	footer.style.marginTop="-22%";
	  	footer.style.paddingTop="3%";
	  	footer.style.paddingBottom="7%";
	  	contenido.style.paddingTop="4%";
	  	contenido.style.paddingBottom="4%";
	  	document.body.style.backgroundColor = "#E5E5E5";
	  	contenido.style.backgroundImage="url( 'img/trevi.jpg')";
	  	contenido.style.backgroundSize="98%";
 }