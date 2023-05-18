class Countries{
  dom;
  modal;
  
  state;  // state variables: entities, entity, mode (Add|Edit)

  constructor(){
    this.state = {'entities': new Array(), 'entity': this.emptyEntity(), 'mode':'A'};
    this.dom=this.render();
    this.modal = new bootstrap.Modal(this.dom.querySelector('#modal'));
    this.dom.querySelector("#countries #create").addEventListener('click',this.makenew);        
    this.dom.querySelector("#countries #search").addEventListener('click',this.search);
    this.dom.querySelector('#countries #modal #form #apply').addEventListener('click',this.add);
  }
  
  render=()=>{
    const html= `
            ${this.renderList()}
            ${this.renderModal()}    
        `;
       var rootContent= document.createElement('div');
       rootContent.id='countries';       
       rootContent.innerHTML=html;
       return rootContent;
  }

   renderList=()=>{
     return `
        <div id="list" class="container">     
            <div class="card bg-light">
                <h4 class="card-title mt-3 text-center">Countries</h4>    
                <div class="card-body mx-auto w-75" >
                    <form id="form">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Name</span>
                            <input id="name" type="text" class="form-control">
                          <div class="btn-toolbar">
                            <div class="btn-group me-2"><button type="button" class="btn btn-primary" id="search">Search</button> </div>
                            <div class="btn-group me-2"><button type="button" class="btn btn-primary" id="create">Create</button> </div>                        
                          </div>  
                        </div>
                    </form>

                    <div class="table-responsive " style="max-height: 300px; overflow: auto">
                        <table class="table table-striped table-hover">
                            <thead><tr><th scope="col">Name</th><th scope="col">Flag</th></tr></thead>
                            <tbody id="listbody">
                            </tbody>
                        </table>
                    </div>                 
                </div>
            </div>
        </div>
        `;
   }
   
   renderModal=()=>{
     return `
        <div id="modal" class="modal fade" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header" >
                        <img class="img-circle" id="img_logo" src="images/logo.png" style="max-width: 50px; max-height: 50px" alt="logo">
                        <span style='margin-left:4em;font-weight: bold;'>Country</span> 
                       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="form" >
                    <div class="modal-body">
                        <h2>Crear País</h2>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Nombre</span>
                            <input type="text" class="form-control" id="name" name="name" required><br><br>
                        </div> 

                        <div class="input-group mb-3">
                            <span class="input-group-text">Capital</span>
                            <input type="text" class="form-control" id="capital" name="capital" required><br><br>
                        </div> 

                        <div class="input-group mb-3">
                            <span class="input-group-text">Poblacion</span>
                            <input type="number" id="population" name="population" required><br><br>
                        </div> 

                        <div class="input-group mb-3">
                            <span class="input-group-text">Area</span>
                            <input type="number" id="area" name="area" required><br><br>
                        </div> 

                        <div class="input-group mb-3">
                            <span class="input-group-text">Latitud </span>
                            <input type="text" class="form-control" id="lat" name="lat" required><br><br>
                        </div> 
                        
                        <div class="input-group mb-3">
                            <span class="input-group-text">Longitud</span>
                            <input type="text" class="form-control" id="lng" name="lng" required><br><br>
                        </div> 

                        <div class="input-group mb-3">
                            <span class="input-group-text">Bandera</span>
                            <input type="text" id="flag" name="flag" required><br><br>
                        </div> 
            
                    </div>
                    <div class="modal-footer">
                        <button id="apply" type="button" class="btn btn-primary" >Agregar</button>
                    </div>
                    </form>                 
                </div>         
            </div>          
        </div>      
        `;     
    }

    showModal= async ()=>{
        // Load entity data into modal form
        this.modal.show();
  }
  
    load=()=>{
        // Save modal form data into entity        
    }
    
    reset=()=>{
        this.state.entity=this.emptyEntity();
    }
    
    emptyEntity=()=>{
        // return an empty entity
    }


add = async  () => {
        // Validate data, load into entity, invoque backend for adding

        var name = document.querySelector('#countries #modal #form #name').value;
        var capital = document.querySelector('#countries #modal #form #capital').value;
        var population = document.querySelector('#countries #modal #form #population').value;
        var area = document.querySelector('#countries #modal #form #area').value;
        var lat = parseFloat(document.querySelector('#countries #modal #form #lat').value);
        var lng = parseFloat(document.querySelector('#countries #modal #form #lng').value);
        let latlng = [lat, lng];
        var flag = document.querySelector('#countries #modal #form #flag').value;
        
        const data = {
            name : name,
            capital : capital,
            population : population,
            area : area,
            latlng : latlng,
            flag : flag
        };

        let request = new Request(`${backend}/countries`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        
        let response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);
            return;
        }

        this.list();
        this.reset();
        this.modal.hide();
    }
  
  update=()=>{
    // Validate data, load into entity, invoque backend for updating    
    this.list();
    this.reset();
    this.modal.hide();
  }
  
   validate=()=>{
       // validate data
  }

  list=()=>{
    const request = new Request(`${backend}/countries`, {method: 'GET', headers: { }});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        var countries = await response.json();
        this.state.entities = countries;
        var listing=this.dom.querySelector("#countries #list #listbody");
        listing.innerHTML="";
        this.state.entities.forEach( e=>this.row(listing,e));         
    })();       
  }  

  row=(list,c)=>{
	var tr =document.createElement("tr");
	tr.innerHTML=`
                <td>${c.name}</td>
                <td><img class="flag" src="${c.flag}"></td>`;              
	list.append(tr);           
  }
  
  makenew=()=>{
      this.reset();
      this.state.mode='A'; //adding
      this.showModal();
  }
    
  search=()=>{
      // To do
  }

} 
