   var map;
   var server = "http://localhost:9000/intour-web";
   markers_pos= [];
   markers_result =[];
   markers_origen = [];
   caracteristicas = [];
   var data = [];
   var aux=0;
   var latOrigen=0;
   var lngOrigen=0;
   var contSel=0;
   var contObt=0;
   var caractSelection= [];
   var POIorigen="Novalue";
   var primeraVez=0;
   var contFavoritos=0;
   var estrellasMarcadas= [];
   var primeraVezFav=0;
   var caractsRuta= [];
   var palabra="";
   var veces=0;
   var auxDIV="titPOISRuta";
   var ticksMarcados=[];
   var primeraVezTick=0;
   var contBusqueda=0;
   var markers_recommendation = [];
   var numEstrellas=0;
    
    
   /*Función que almacena las localizaciones en un array según las caracteristicas seleccionadas*/
   function loadResults (data) {
      
      var items = [];
      var numPOIS=caractSelection.length;
      var numFinal=numPOIS*5+numPOIS; 
      veces++;
      
      if (data.suggestion.length > 0) {
        items = data.suggestion;
        
        for (var i = 0; i < items.length; i++) {
          var item = items[i];
          if (item.lat != undefined && item.lon != undefined) {
		    
		    if(item.lat !="" && item.lon !=""){
	            markers_pos.push({
	              all: item,
	              lat : item.lat,
	              lng : item.lon});
			    }
 
          }
        }
      }
      	
      	console.log('numPOIS: '+numPOIS);
      	console.log('veces: '+veces);
      	console.log('markers_pos.length: '+markers_pos.length);
      	console.log('markers_result.length: '+ markers_result.length);
      	
      	if(markers_pos.length!=0 && veces==numPOIS && markers_pos.length>=numPOIS && markers_pos.length<=numFinal){
      		veces=0;
      		calcularRutaREST();
      	}

    }
   
 
	/*Función que elimina aquellos POIS repetidos previamente al cálculo de la ruta*/
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
 
 
  /*Función que realiza el cálculo de la ruta según una serie de POIS*/
	function calcularRutaREST(){
	
		primeraVezTick=0;
		ticksMarcados.splice(0,ticksMarcados.length);
	   //Cargamos los datos para enviarles al servicio de calculo de ruta
	   var aux=0;
	   map.removeMarkers(markers_result);
	   console.log('latOrigen: '+latOrigen+', lngOrigen: '+lngOrigen);
	   
	   for(var i=0; i< markers_pos.length;i++){
	   		eliminarRepetidos(i);
	   }
	   
	 //Marker.all:item -> POI entero
	   var pois = [];
	   for(var i=0; i<markers_pos.length;i++){
		   pois.push(data[i].all);
	   }
	   var markers_request = JSON.stringify(pois);
	   
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
	    	   var items = [];
	    	   items = response.camino;
	        
	        if(primeraVez==0){
  				primeraVez++;
	        }else{
	        	$("#titPOISRuta").remove();
	        	$("#POISRuta").remove();
	        }
	        $("#resultsRoute").remove();
	        $("#Mapa").append("<div id='resultsRoute'><h2 id='titPOISRuta'> Puntos en su Ruta: </h2></div>");
            
		        for (var i = 0; i < items.length; i++) {
				
		          princi=0;	
		          var item = items[i];
		          
		          if (item.lat != undefined && item.lon != undefined) {
		          
		          console.log('item.lat '+i+': '+item.lat+'item.lon '+i+':'+item.lon);
		            //var icon = server+'/img/poi.png';//FIXME per type
		            var icon= server+item.icono;
		            if(item.icono==""){
		            	icon="";
		            }
		            var imgUri=item.picture;
		            var imgURL ="<p style='text-align:center'><img src='"+imgUri+"'/></p>";
		            var infoContent = "<ul><li>Caracteristica: "+item.name+"</li><li>Latitud: "+
		            item.lat+"</li><li>Longitud: "+item.lon+"</li>";
		            if(item.address!=""){
		            	infoContent= infoContent + "<li>Direccion: "+item.address+"</li>";
		            }
		            if(item.fuente !=""){
		            	infoContent= infoContent + "<li>Fuente: "+item.fuente+"</li>";
		            }
		            infoContent= infoContent+"</ul>"+ imgURL;
		            
				     map.addMarker({
		              lat : item.lat,
		              lng : item.lon,
		              title : item.name,
		              icon: icon,
		              infoWindow: {
		        		content : infoContent
				      }
		            });
		            markers_result.push({
		            	all : item,
		            	lat : item.lat,
		              	lng : item.lon,	
		            });
		            
		            //Dibujamos la ruta
		            aux=i+1;
		            if(aux<=items.length-1){
					
						map.drawRoute({
						  origin: [items[i].lat, items[i].lon],
						  destination: [items[i+1].lat, items[i+1].lon],
						  travelMode: 'driving',
						  strokeColor: '#131540',
						  strokeOpacity: 0.6,
						  strokeWeight: 6
						});
					}
		            
		            if(i==0){
		            	$("#titPOISRuta").after("<div class='DIVRoute' id='DIV"+i+"'><img class='men' id='man"+i+"' src='img/hombre.png'></div>");
				        $("#man"+i).after("<button class='botonesRoute' id='BOTON"+i+"' type='button' onclick='visualizarInformacion("+i+")'>"+i+"</button>");
		            	$("#BOTON"+i).after("<p class='poisImportant' id='punto"+i+"'>Punto de Origen</p>");
		            	$("#punto"+i).after("<img class='ticks' id='tick"+i+"' onclick='verificar("+i+")' src='img/casilla.png'>");
		            	$("#tick"+i).after("<p class='infopuntos' id='info"+i+"'>Lugar: "+$("#POIorigen").val()+", Latitud: "+items[i].lat+", Longitud: "+items[i].lon+"</p>");
		            }else{
		            	var auxx=i-1;
	            		$("#DIV"+auxx).after("<div class='DIVRoute' id='DIV"+i+"'><img class='men' id='man"+i+"' src='img/hombre.png'></div>");
				        $("#man"+i).after("<button class='botonesRoute' id='BOTON"+i+"' type='button' onclick='visualizarInformacion("+i+")'>"+i+"</button>");
	            		$("#BOTON"+i).after("<p class='poisImportant' id='punto"+i+"'>"+items[i].name+"</p>");
	            		$("#punto"+i).after("<img class='ticks' id='tick"+i+"' onclick='verificar("+i+")' src='img/casilla.png'>");
	            		$("#tick"+i).after("<p class='infopuntos' id='info"+i+"'>Latitud: "+items[i].lat+", Longitud: "+items[i].lon+"</p>");
		            	
		            }
		            
		          }
		        }
		    
	         markers_pos.splice(0, markers_pos.length);
	         //Copiamos los puntos de la ruta antes de que se eliminen
	         for(var z=0; z<markers_result.length; z++){
	        	 markers_recommendation.push(markers_result[z].all);
	         }
      		 markers_result.splice(0,markers_result.length);
      		 data.splice(0, data.length);
      		 estrellasMarcadas.splice(0,estrellasMarcadas.length);
      		 primeraVezFav=0;
	      	 valorarRuta();
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

	/*Función que inicializa el mapa*/
	 function initMap(){
	  map = new GMaps({
	        div: '#map',
	        lat: 40.4000,
	        lng: 3.7167
	      });
	
	      map.on('marker_added', function (marker) {
	        var index = map.markers.indexOf(marker);
	        if (index == map.markers.length - 1) {
	          map.fitZoom();
	        }
	      });
	 }
  
	 function visualizarInformacion(i){
		
		var auxinfo="info"+i;
		var info = document.getElementById(auxinfo);
		var men=document.getElementsByClassName("men");
		info.style.marginTop="-1%";
		info.style.visibility="visible";
		men[i].style.visibility="visible";
		$("#BOTON"+i).remove();
		$("#man"+i).after("<button class='botonesRoute' id='BOTON"+i+"' type='button' onclick='ocultarInformacion("+i+")'>"+i+"</button>");
	 }
	 
	 function ocultarInformacion(i){
		 
		var auxinfo="info"+i;
		var info = document.getElementById(auxinfo);
		var men=document.getElementsByClassName("men");
		info.style.marginTop="-5%";
		info.style.visibility="hidden";
		men[i].style.visibility="hidden";
		$("#BOTON"+i).remove();
		$("#man"+i).after("<button class='botonesRoute' id='BOTON"+i+"' type='button' onclick='visualizarInformacion("+i+")'>"+i+"</button>");		 
	 }
	 
	 function verificar(idtick){
		 
		 var index=999999;
		 var auxtick=idtick-1;
		 var auxver="tick"+idtick;
		 
		 for(var j=0; j<ticksMarcados.length; j++){
		  		if(idtick==ticksMarcados[j]){
		  			index=ticksMarcados.indexOf(idfav);
		  		}
		  		console.log('ticksMarcados '+j+' : '+ ticksMarcados[j]);
		 }
		 
		 if(index==999999 && (ticksMarcados[ticksMarcados.length-1]==auxtick || (primeraVezTick==0 && idtick==0))){
			 
			 ticksMarcados.push(idtick);
			 primeraVezTick=1;
			 $("#"+auxver).remove();
			 $("#punto"+idtick).after("<img class='ticks' id='tick"+idtick+"' onclick='noverificar("+idtick+")' src='img/comprobado.png'>");
			 
		 }
		 
		
	 }
	 
	 function noverificar(idtick){
		 
		 var auxnover="tick"+idtick;
		 
		 if(idtick==ticksMarcados[ticksMarcados.length-1]){
			 
			ticksMarcados.splice(ticksMarcados.length-1,1);
	  		if(ticksMarcados.length==0){
	  			primeraVezTick=0;
	  		}
	  		 $("#"+auxnover).remove();
			 $("#punto"+idtick).after("<img class='ticks' id='tick"+idtick+"' onclick='verificar("+idtick+")' src='img/casilla.png'>");
	  		
		 }
		 
		
	 }
	 
	 function valorarRuta(){
	  	
	  	$("#resultsRoute").append("<div id='DIVvaloracion'><h3 id='valoracion'>Valora tu ruta:</h3></div>");
	  	var favoritoId;
	  	for(var i=0; i<5; i++){
	  		favoritoId='favorito'+i;
	  		$("#valoracion").after("<img class='starts' id='"+favoritoId+"' src='img/favorito.png' onclick='addFavoritos("+i+")' alt='Icono de favorito'>");
	  	}
	  	$("#valoracion").after("<button onclick='guardarValoracion()' id='botonValorar' type='button'>Valorar</button>");
	  
	  }
	  
	  function addFavoritos(idfav){
	  	var cont=0;
	  	var auxidfav=idfav+1;
	  	var favoritoId;
	  	var index=999999; //Por defecto tiene ese valor
	  	for(var h=0; h<estrellasMarcadas.length; h++){
	  		
	  		if(idfav==estrellasMarcadas[h]){
	  			index=estrellasMarcadas.indexOf(idfav);
	  		}
	  		console.log('estrellasMarcadas '+h+' : '+ estrellasMarcadas[h]);
	  	}
	  	
	  	if(index==999999 && (estrellasMarcadas[estrellasMarcadas.length-1]==auxidfav || (primeraVezFav==0 && idfav==4))){
	  		estrellasMarcadas.push(idfav);
	  		primeraVezFav=1;
	  		//Eliminamos las estrellas anteriores
	  	  	for(var z=0; z<5; z++){
	  	  		favoritoId='favorito'+z;
	  	  		$("#"+favoritoId).remove();	
	  	  	}
	  	  	//Actualizamos las estrellas pulsadas y las que no
	  	  	for(var i=0; i<5; i++){
	  	  		favoritoId='favorito'+i;
	  	  		for(var j=0; j<estrellasMarcadas.length; j++){
	  	  			if(i==estrellasMarcadas[j]){
	  	  				$("#valoracion").after("<img class='starts' id='"+favoritoId+"' src='img/favoritoRelleno.png' onclick='removeFavoritos("+i+")' alt='Icono de favorito'>");
	  	  				break;
	  	  			}else{
	  		  			cont++;	
	  		  		}
	  	  		}
	  	  		if(cont==estrellasMarcadas.length){
	  		  			$("#valoracion").after("<img class='starts' id='"+favoritoId+"' src='img/favorito.png' onclick='addFavoritos("+i+")' alt='Icono de favorito'>");
	  		  	}
	  	  		cont=0;
	  	  	}  	
	  	}
	  		
	  
	  }
	 
	 
	  function  removeFavoritos(idfav){
	  
	  	var favoritoId;
	  	var contRem=0;
	  	
	  	if(idfav==estrellasMarcadas[estrellasMarcadas.length-1]){
	  		estrellasMarcadas.splice(estrellasMarcadas.length-1,1);
	  		if(estrellasMarcadas.length==0){
	  			primeraVezFav=0;
	  		}
	  		//Eliminamos las estrellas anteriores
	  		for(var z=0; z<5; z++){
	  	  		favoritoId='favorito'+z;
	  	  		$("#"+favoritoId).remove();	
	  	  	}
	  		//Actualizamos las estrellas pulsadas y las que no
	  		for(var i=0; i<5; i++){
	  	  		favoritoId='favorito'+i;
	  	  		for(var j=0; j<estrellasMarcadas.length; j++){
	  	  			if(i==estrellasMarcadas[j]){
	  	  				$("#valoracion").after("<img class='starts' id='"+favoritoId+"' src='img/favoritoRelleno.png' onclick='removeFavoritos("+i+")' alt='Icono de favorito'>");
	  	  				break;
	  	  			}else{
	  	  				contRem++;	
	  		  		}
	  	  		}
	  	  		if(contRem==estrellasMarcadas.length){
	  		  			$("#valoracion").after("<img class='starts' id='"+favoritoId+"' src='img/favorito.png' onclick='addFavoritos("+i+")' alt='Icono de favorito'>");
	  		  	}
	  	  		contRem=0;
	  	  	}  	
	  		
	  	} 	
	  
	  }
	  
	  function guardarValoracion(){
		  
		  var caractsAuxRequest = [];
		  numEstrellas=estrellasMarcadas.length;
		  var aux;
		  for(var i=0; i<caractsRuta.length; i++){
			  aux=i+1;
			  if(i==0 || caractsRuta[i-1]=='#'){
					  caractsAuxRequest.push(caractsRuta[i]);
				 
			  }else if(caractsRuta[i]=='#'){
				  caractsAuxRequest.push('#');
			  }else{
				  for(var j=0; j<caractSelection.length; j++){	  
					  
					  if(caractsRuta[i]==caractSelection[j].name){
						  caractsAuxRequest.push(caractsRuta[i]);
					  }
				  }
			  }	  
		  }
		  
		  //Comprobamos que es correcto aquello que vamos a enviar
		  for(var z=0; z<caractsAuxRequest.length; z++){
			  console.log('caractsAuxRequest '+z+': '+caractsAuxRequest[z]); 
		  }
		  
		  //Enviamos los datos con la valoracion del usuario
		  var caractsRequest = JSON.stringify(caractsAuxRequest);
		  var serializedData='{"POIorigen":\"'+POIorigen+'\","numEstrellas":'+numEstrellas+',"caracts":'+caractsRequest+'}';
		  console.log(serializedData);
		   var request = $.ajax({
		        url: server+'/services/valorar',
		        async: true,
		        headers: {          
		                 Accept : "text/plain; charset=utf-8",         
		                "Content-Type": "application/json; charset=utf-8"   
		  		},  
		        type: "post",
		        data: serializedData,
		        success: function(data, textStatus, jqXHR)
		        {
		        	$("#Mapa").append("<div id='dialogAlertaValorar'><p>INFORMACI&Oacute;N: La ruta ha sido valorada correctamente</p><br><div>");
					$("#dialogAlertaValorar").dialog({
					    modal: true,
			        	height: 225,
			      		width: 374,
			      		buttons: {
							"Aceptar":function(){
								$( this ).dialog( "close" );
							}        
					    }
			    	});
		            console.log("success",data);
		            recomendar();
		        },
		        error: function (jqXHR, textStatus, errorThrown)
		        { 
		        	$("#Mapa").append("<div id='dialogErrorValorar'><p>ERROR: No ha sido posible valorar la ruta. Int&eacute;ntelo m&aacute;s tarde.</p><br><div>");
					$("#dialogErrorValorar").dialog({
					    modal: true,
			        	height: 225,
			      		width: 374,
			      		buttons: {
							"Aceptar":function(){
								$( this ).dialog( "close" );
							}        
					    }
			    	});
		        	console.log("error"+jqXHR);
		        }
		    });
		  
	  }
		  
  
  function recomendar(){
	  //numEstrellas=3;
	//Enviamos los datos con la valoracion del usuario
	  var markersRequest = JSON.stringify(markers_recommendation);
	  var serializedData='{"numStarts":'+numEstrellas+',"markers":'+markersRequest+'}';
	  console.log(serializedData);
	   var request = $.ajax({
	        url: server+'/services/recommend',
	        async: true,
	        headers: {          
	                 Accept : "text/plain; charset=utf-8",         
	                "Content-Type": "application/json; charset=utf-8"   
	  		},  
	        type: "post",
	        data: serializedData,
	        success: function(data, textStatus, jqXHR)
	        {
	            console.log("success",data);
	        },
	        error: function (jqXHR, textStatus, errorThrown)
	        { 
	        	console.log("error"+jqXHR);
	        }
	    });
	  
	  
  }	  
  
  function search(name,input) {
  
         if (name!= null) {
            initMap();//FIXME: Check
         	var xhr = $.getJSON(server+'/services/search?namePlace='+name+'&input='+input);
         	xhr.done(loadResults);
         }
  }
  
  function generarRuta(){
	  	var dialogOrigen = document.getElementById("dialogOrigen");
		var mapa = document.getElementById("Mapa");
		var inputPOIorigen= document.getElementById("POIorigen");
		var numscaract=caractSelection.length;
		var auxName;
		var auxInput;
		if(numscaract<2){
			$("#Mapa").append("<div id='dialogAlertaRoute'><p>IMPORTANTE: Es necesario seleccionar m&aacute;s de 2 caracter&iacute;sticas para generar la ruta</p><br><div>");
			$("#dialogAlertaRoute").dialog({
			    modal: true,
	        	height: 225,
	      		width: 374,
	      		buttons: {
					"Aceptar":function(){
						$( this ).dialog( "close" );
					}        
			    }
	    	});
		}else{
			
			dialogOrigen.style.visibility="visible";
			dialogOrigen.style.marginTop="0%";
			inputPOIorigen.style.marginTop="3%";
			$("#dialogOrigen").dialog({
			    modal: true,
	        	height: 300,
	      		width: 330,
	        	buttons: {
					"Aceptar": function(){
						$( this ).dialog( "close" );
						POIorigen= $("#POIorigen").val();
						console.log('POI-ORIGEN: '+POIorigen);
				  		if(POIorigen!=""){ 
					    	mapa.style.marginTop="3%";
					     	mapa.style.visibility="visible";
					    	mapa.style.marginTop="3%";
					     	mapa.style.visibility="visible";
					     
					     	localizarOrigen(POIorigen);
					  		for(var i=0; i<caractSelection.length; i++){
					  			
					  			console.log('Caract '+i+' : '+caractSelection[i].name);
					  			auxName=caractSelection[i].name;
					  			auxInput=caractSelection[i].input;
					  			search(auxName,auxInput);	
					  		}	
				  		}		
					},
					Cancel: function(){
						$( this ).dialog( "close" );
					}        
			    }
	    	});
		
		}
		
    	
    	    
  	    
  }
  
  function localizarOrigen(origen){
  	
  		var name=origen;
		var input='7';
  		initMap();//FIXME: Check
        var xhr = $.getJSON(server+'/services/search?namePlace='+name+'&input='+input);
        xhr.done(loadResultsOrigen);
  }
  
  function loadResultsOrigen(data){
      
	  
	      map.removeMarkers(markers_origen);
	      var items =[];
	   
	      if (data.suggestion.length > 0) {
	        items = data.suggestion;
	        for (var i = 0; i < items.length; i++) {
	          var item = items[i];
	          if (item.lat != undefined && item.lon != undefined) {
			    
			    if(item.lat !="" && item.lon !=""){
			    	latOrigen=item.lat;
			    	lngOrigen=item.lon;
			    	console.log('latOrigenMetodo: '+latOrigen+', lngOrigenMetodo: '+lngOrigen);
			    	markers_origen.pop();
				   	markers_origen.push({
		              lat : item.lat,
		              lng : item.lon,
		              
		            });
	 
	          }
	        }
	      }
	      	//map.addMarkers(markers_origen);   	
	  }
 } 
  
  function clearInputs(){
     
     for(var i=0; i<caracteristicas.length; i++){
        caracteristicas[i]="";
     }
  }
  
  function mostrarInputs(){
     
      var obtenidos= document.getElementById("obtenidos");
      var seleccionados= document.getElementById("seleccionados");
      var auxParam;
      var imgId;
      var segundoId;
      var numcaracts=$('#obtenidos').find('div').length;
      console.log("numcaracts: "+numcaracts);
      var divd;
      var entero;
      
      for(var i=0; i<caracteristicas.length; i++){
      
        var divRes = $('<div class="caracts"></div>');
      	var boton = document.createElement('input');
      	if(caracteristicas[i].valor!=undefined){
      	
      		//divd=numcaracts/2;
      		//console.log("divd: "+divd);
      	    //contObt= contObt+4
      	    //contSel= contSel+1;
      		obtenidos.style.paddingBottom="25%";
      		seleccionados.style.paddingBottom="15%";
      		/*CREAMOS EL DIV DE LA CARACTERISTICA*/
      		contenedor = document.createElement('div');
  			contenedor.id = 'div'+i;
  			contenedor.className='divsCaractObt';
  			obtenidos.appendChild(contenedor);
  			var conten = document.getElementById(contenedor.id);
  			divsCaracts = document.getElementsByClassName(contenedor.className);
  			conten.style.cssFloat = "left";
  			conten.style.marginLeft="2%";
  			conten.style.marginRight="2%";
  			conten.style.marginTop="2%";
  			 
  			
  			/*CREAMOS EL BOTON*/
  			boton.type='button';
  			boton.id='boton'+i;
  			boton.className='resultsCaract';
  			boton.value=caracteristicas[i].valor;
  			auxParam=caracteristicas[i].tipo;
      		conten.appendChild(boton);
      		imgId='img'+i;
      		var auxId= document.getElementById(boton.id);
      		auxId.style.backgroundColor="#0101FB";
      		auxId.style.color="white";
      		auxId.style.border="none";
      		auxId.style.padding="4px 20px";
      		auxId.style.borderRadius="20px";
      		auxId.style.fontFamily="'Century Gothic', CenturyGothic, AppleGothic, sans-serif";
      		auxId.style.fontWeight="700";
      		
      		$(auxId).after("<img class='adds' id='"+imgId+"' src='img/add2.png' name='"+auxParam+"' onclick='addInputs("+i+")' alt='Icono de anadir' style='width: 20px; cursor: pointer; float:left;'>");
      	   
      	
      	}
     
     }
     
     
     
  }
  
  function addInputs(i){
  	
	  	var name=document.getElementById('boton'+i).value;
	    var input=document.getElementById('img'+i).name;
	    var obtenidos= document.getElementById("obtenidos");
	    var seleccionados= document.getElementById("seleccionados");
	    var divAux=document.getElementById('div'+i);
	    obtenidos.removeChild(divAux);
	    
	    caractSelection.push({
			  		name: name,
			  		input: input
		});
		
	    //contObt=contObt-3; 
	    //contSel= contSel+3;
	    //obtenidos.style.paddingBottom=contObt+"%";
	    //seleccionados.style.paddingBottom=contSel+"%";
	    /*CREAMOS EL DIV DE LA CARACTERISTICA*/
      		contenedor = document.createElement('div');
  			contenedor.id = 'div'+i;
  			contenedor.className='divsCaract';
  			seleccionados.appendChild(contenedor);
  			var conten = document.getElementById(contenedor.id);
  			conten.style.cssFloat = "left";
  			conten.style.marginLeft="2%";
  			conten.style.marginRight="2%";
  			conten.style.marginTop="2%";
    	/*CREAMOS EL BOTON*/
    		var boton = document.createElement('input');
  			boton.type='button';
  			boton.id='boton'+i;
  			boton.className='resultsCaract';
  			boton.value=name;
  			auxParam=input;
      		conten.appendChild(boton);
      		imgId='img'+i;
      		var auxId= document.getElementById(boton.id);
      		auxId.style.backgroundColor="#D80027";
      		auxId.style.color="white";
      		auxId.style.border="none";
      		auxId.style.padding="4px 20px";
      		auxId.style.borderRadius="20px";
      		auxId.style.fontFamily="'Century Gothic', CenturyGothic, AppleGothic, sans-serif";
      		auxId.style.fontWeight="700";
      		
      		$(auxId).after("<img class='deletes' id='"+imgId+"' src='img/remove2.png' name='"+auxParam+"' onclick='borrarInputs("+i+")' alt='Icono de anadir' style='width: 20px; cursor: pointer; float:left;'>");
  
  }
  
  function borrarInputs(i){
	  	 
	  	 var cont=5;
	  	 var name=document.getElementById('boton'+i).value;
		 var input=document.getElementById('img'+i).name;
	  	 var obtenidos= document.getElementById("obtenidos");
		 var seleccionados= document.getElementById("seleccionados");
		 var divAux=document.getElementById('div'+i);
		 seleccionados.removeChild(divAux);
		 
		 for(var j=0; j<caractSelection.length; j++){
		 	if(caractSelection[j].name==name && caractSelection[j].input==input ){
		 		caractSelection.splice(j,1);
		 	}
		 }
		  
		  //contSel= contSel-4;
		  //seleccionados.style.paddingBottom=contSel+"%";
		 
	  	/*CREAMOS EL DIV DE LA CARACTERISTICA*/
	      		contenedor = document.createElement('div');
	  			contenedor.id = 'div'+i;
	  			contenedor.className='divsCaractObt';
	  			obtenidos.appendChild(contenedor);
	  			var conten = document.getElementById(contenedor.id);
	  			divsCaracts = document.getElementsByClassName(contenedor.className);
	  			conten.style.cssFloat = "left";
	  			conten.style.marginLeft="2%";
	  			conten.style.marginRight="2%";
	  			conten.style.marginTop="2%";
	  			 
	  			
	  			/*CREAMOS EL BOTON*/
	  			var boton = document.createElement('input');
	  			boton.type='button';
	  			boton.id='boton'+i;
	  			boton.className='resultsCaract';
	  			boton.value=name;
	  			auxParam=input;
	      		conten.appendChild(boton);
	      		imgId='img'+i;
	      		var auxId= document.getElementById(boton.id);
	      		auxId.style.backgroundColor="#0101FB";
	      		auxId.style.color="white";
	      		auxId.style.border="none";
	      		auxId.style.padding="4px 20px";
	      		auxId.style.borderRadius="20px";
	      		auxId.style.fontFamily="'Century Gothic', CenturyGothic, AppleGothic, sans-serif";
	      		auxId.style.fontWeight="700";
	      		
	      		$(auxId).after("<img class='adds' id='"+imgId+"' src='img/add2.png' name='"+auxParam+"' onclick='addInputs("+i+")' alt='Icono de anadir' style='width: 20px; cursor: pointer; float:left;'>");
		 
  }
  
  function loadResultsEntity (data) {
      var items;
   	  var valores = document.getElementsByClassName("valores");
   	  var aux="";
   	  
      if (data.suggestion.length > 0) {
        items = data.suggestion;
        
        if(aux!=palabra){
          caractsRuta.push('#');  
      	  caractsRuta.push(palabra);
        }
        

        for (var i = 0; i < items.length; i++) {
          var item = items[i];
          var infoContent = "<ul><li>Name:"+item.name+"</li><li>birthPlace:"+ item.birthPlace+"</li><li>birthDate:"+item.birthDate+"</li><li>Description:"+item.description+"</li><li>Nationality:"+item.nationality+"</li><li>Field:"+item.field+"</li><li>Museum:"+item.museum+"</li></ul>";
		  
           
		  clearInputs();
		  	  if(item.name!=""){
			  
			  	caracteristicas.push({
			  		valor: item.name,
			  		tipo: '0'
			  	});
			  	caractsRuta.push(item.name);
			  }
			  if(item.birthPlace!=""){
			  	
			  	caracteristicas.push({
			  		valor: item.birthPlace,
			  		tipo: '1'
			  	});
			  	caractsRuta.push(item.birthPlace);
			  }
			  if(item.birthDate!=""){
			  
			  	caracteristicas.push({
			  		valor: item.birthDate,
			  		tipo: '2'
			  	});
			  	caractsRuta.push(item.birthDate);
			  }
			  if(item.description!=""){
			  	
			  	caracteristicas.push({
			  		valor: item.description,
			  		tipo: '3'
			  	});
			  	caractsRuta.push(item.description);
			  }
			  if(item.nationality!=""){
			  	caracteristicas.push({
			  		valor: item.nationality,
			  		tipo: '4'
			  	});
			  	caractsRuta.push(item.nationality);
			  }
			  if(item.field!=""){
			  	caracteristicas.push({
			  		valor: item.field,
			  		tipo: '5'
			  	});
			  	caractsRuta.push(item.field);
			  }
			  if(item.museum!=""){
			  	caracteristicas.push({
			  		valor: item.museum,
			  		tipo: '6'
			  	});
			  	caractsRuta.push(item.museum);
			  }
			  aux=palabra;
			  mostrarInputs();
        }
        for(var j=0; j<caractsRuta.length; j++){
			  console.log('caractsRuta '+j+': '+caractsRuta[j]);  
		}
      }
      
      
   }
  
  
  function searchEntity(){
  		
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
	       	palabra=name;
	       	caracteristicas.style.visibility="visible";
	       	caracteristicas.style.marginTop="-24%";
	       	caracteristicas.style.marginBottom="6%";
	       	caracteristicas.style.marginLeft="-24px";
	       	ccaracteristicas.style.paddingBottom="4%";
	       	if(contBusqueda==0){
	       		obtenidos.style.backgroundColor="white";
		       	obtenidos.style.height="50%";
		       	obtenidos.style.marginLeft="5%";
		       	obtenidos.style.width="85%";
		       	obtenidos.style.padding="2%";
		       	obtenidos.style.border="3px solid #444444";
		       	obtenidos.style.borderRadius = "35px";
		       	seleccionados.style.backgroundColor="white";
		       	seleccionados.style.height="50%";
		       	seleccionados.style.marginLeft="5%";
		       	seleccionados.style.width="85%";
		       	seleccionados.style.padding="2%";
		       	seleccionados.style.border="3px solid #444444";
		       	seleccionados.style.borderRadius = "35px";
	       	}
	       	
	       	contBusqueda=1;
	       	footer.style.marginTop="0%";
	       	footer.style.paddingTop="0%";
	       	footer.style.paddingBottom="6%";
	       	contenido.style.backgroundImage="none";
	       	ccontenido.style.marginTop="-1%";
	       	document.body.style.backgroundColor="white";
	       	contenido.style.backgroundImage="url('img/artee.jpg')";
	       	contenido.style.backgroundSize="100% 58%";
	       	$(".pasos").remove();
	       	$(".divsCaractObt").remove();
	       	var xhr = $.getJSON(server+'/services/entity?name='+name);
	       	xhr.done(loadResultsEntity);
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
	  	var dialogOrigen = document.getElementById("dialogOrigen");
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
	  	contenido.style.backgroundSize="100%";
	  	dialogOrigen.style.marginTop="1%";
	  	
 }
 