// Guardar el fetch original
const originalFetch = window.fetch;

window.fetch = async function (...args) {
    console.log('Interceptando fetch:', args);

    // Puedes modificar la solicitud aquí si es necesario
    let [resource, config] = args;
    console.log('URL:', resource);

    if (config) {
        console.log('request:', config);

        if (resource !== 'login') {
            const userData = JSON.parse(localStorage.getItem('userData'));
            config.headers['Authorization'] = 'Bearer '+userData.token;
            //config.headers.push({Authorization: 'Bearer '+userData.token});
            console.log('MODIFICARRRRRRR',config.headers);
        }
    }
    // Llamar al fetch original
    const response = await originalFetch(...args);

    // Puedes modificar la respuesta aquí si es necesario
    console.log('Respuesta interceptada:', response);

    return response; // Retornar la respuesta (modificada o no)
};

document.addEventListener("DOMContentLoaded", () => {
    console.log('Ejecutando F5');

    // limpiar localStorage luego del login
    if (window.location.pathname === "/login") {
        // Ejecutar código específico
        console.log('Limpieza de localstorage');
        limpiarLocalStorare();
    }

    if (window.location.pathname === "/") {
        // Ejecutar código específico
        console.log('Limpieza de localstorage');
        limpiarLocalStorare();
    }


    if (window.location.pathname === "/venta/orden") {
        // Ejecutar código específico
        generarPreOrden();
    }

    if (window.location.pathname === "/venta/notaventa") {
        // Ejecutar código específico
        generarNotaVenta();
    }

    if (window.location.pathname === "/venta/") {
    }

    enabledButon();
    cargarMenuLeft();
});

async function agregarProducto(any) {

    // Obtener la fila que contiene el botón
    const fila = any.closest('tr');

    // Obtener los atributos personalizados
    const productId = fila.getAttribute('product-id');
    const name = fila.getAttribute('name');
    const price = fila.getAttribute('price');
    const image = fila.getAttribute('image');

    const product = {productId: productId, name: name, image: image, price: price, quantity: 1};

    // obtener productos si existe
    let products = JSON.parse(localStorage.getItem('products'));
    let totalPrice = 0;
    let totalQuantity = 0;
    console.log(products);
    if (products) {

        // validar si existe el producto
        const existProduct = products[productId];
        if (existProduct) {
            existProduct.quantity = Number(existProduct.quantity) + 1;
        } else {
            products[productId] = product;
        }

        const price = Number(localStorage.getItem('totalPrice')) + Number(product.price);
        totalPrice = price;
        localStorage.setItem('products', JSON.stringify(products));
        localStorage.setItem('totalPrice', price.toString());
    } else {
        products = {};
        products[productId] = product;
        localStorage.setItem('products', JSON.stringify(products));
        localStorage.setItem('totalPrice', price);
        totalPrice = price;
    }
    console.log('producto :', products);
    document.getElementById('detailproduct').style.display = 'block';

    totalQuantity = Number(localStorage.getItem('totalQuantity')) + 1;
    localStorage.setItem('totalQuantity', totalQuantity.toString());
    document.querySelector('#detailproduct-a').textContent = '(' + totalQuantity + ')-' + totalPrice + 'Bs. Ordenar';
}

async function quitarProducto(any) {
    // Obtener la fila que contiene el botón
    const fila = any.closest('tr');

    // Obtener los atributos personalizados
    const productId = fila.getAttribute('product-id');
    const name = fila.getAttribute('name');
    const price = fila.getAttribute('price');
    const image = fila.getAttribute('image');

    const product = {productId: productId, name: name, price: price, image: image, quantity: 1};

    // obtener productos si existe
    let products = JSON.parse(localStorage.getItem('products'));
    let totalPrice = 0;
    let totalQuantity = 0;
    console.log(products);
    if (products) {

        // validar si existe el producto
        const existProduct = products[productId];
        if (existProduct) {
            console.log('existe el producto :', products);
            if (existProduct.quantity > 0) {
                existProduct.quantity = Number(existProduct.quantity) - 1;
                if (existProduct.quantity === 0) {
                    delete products[productId];
                }
                const price = Number(localStorage.getItem('totalPrice')) - Number(product.price);
                totalPrice = price;
                localStorage.setItem('products', JSON.stringify(products));
                localStorage.setItem('totalPrice', price.toString());
                totalQuantity = Number(localStorage.getItem('totalQuantity')) - 1;
                localStorage.setItem('totalQuantity', totalQuantity.toString());
                document.querySelector('#detailproduct-a').textContent = '(' + totalQuantity + ')-' + totalPrice + 'Bs. Ordenar';

                if (totalQuantity === 0) {
                    document.getElementById('detailproduct').style.display = 'none';
                }
            }
        }
    } else {
        document.getElementById('detailproduct').style.display = 'none';
    }
}


async function generarPreOrden() {

    const products = JSON.parse(localStorage.getItem('products'));

    let litadoOrden = '';
    for (const clave in products) {
        if (clave !== null && clave !== undefined) {
            const valor = products[clave];
            let productHtml = '<tr>\n' +
                '<td>' + valor.name + '</td>\n' +
                '<td id="colprice' + clave + '"> Bs' + valor.price + '</td>\n' +
                '<th scope="row" id="colquantity' + clave + '">' + valor.quantity + '</th>\n' +
                '<td>\n' +
                '<button type="button" class="btn btn-danger" id="colbtn' + clave + '" onclick="quitarProductoOrd(' + valor.productId + ')"> - </button>' +
                '<button type="button" class="btn btn-success" onclick="agregarProductoOrd(' + valor.productId + ')">+</button>\n' +
                '</td></tr>';
            litadoOrden += productHtml;
        }
    }
    document.querySelector('#resumentotal').textContent = 'Total: Bs' + localStorage.getItem('totalPrice');
    document.querySelector('#preorden tbody').outerHTML = litadoOrden;
}

async function quitarProductoOrd(idProduct) {
    let products = JSON.parse(localStorage.getItem('products'));
    let productToUpdate = products[idProduct];
    productToUpdate.quantity = Number(productToUpdate.quantity) - 1;

    if (productToUpdate.quantity >= 0) {
        products[idProduct] = productToUpdate;

        localStorage.setItem('products', JSON.stringify(products));

        const price = Number(localStorage.getItem('totalPrice')) - Number(productToUpdate.price);
        localStorage.setItem('totalPrice', price.toString());

        const totalQuantity = Number(localStorage.getItem('totalQuantity')) - 1;
        localStorage.setItem('totalQuantity', totalQuantity.toString());

        document.querySelector('#colquantity' + idProduct).textContent = productToUpdate.quantity;
        document.querySelector('#resumentotal').textContent = 'Total: Bs' + price.toString();
    } else {
        if (document.querySelector('#colbtn' + idProduct).textContent === 'Elim.') {
            delete products[idProduct];
            localStorage.setItem('products', JSON.stringify(products));
            generarPreOrden();
        } else {
            document.querySelector('#colbtn' + idProduct).textContent = 'Elim.';
        }
    }
}

async function agregarProductoOrd(idProduct) {
    let products = JSON.parse(localStorage.getItem('products'));
    let productToUpdate = products[idProduct];
    productToUpdate.quantity = Number(productToUpdate.quantity) + 1;
    products[idProduct] = productToUpdate;
    localStorage.setItem('products', JSON.stringify(products));

    const price = Number(localStorage.getItem('totalPrice')) + Number(productToUpdate.price);
    localStorage.setItem('totalPrice', price.toString());

    const totalQuantity = Number(localStorage.getItem('totalQuantity')) + 1;
    localStorage.setItem('totalQuantity', totalQuantity.toString());

    document.querySelector('#colquantity' + idProduct).textContent = productToUpdate.quantity;
    document.querySelector('#resumentotal').textContent = 'Total: Bs' + price.toString();
    if (productToUpdate.quantity === 1) {
        document.querySelector('#colbtn' + idProduct).textContent = '-';
    }
}

async function enabledButon() {
// Validar si hay produtos en memoria
    let products = JSON.parse(localStorage.getItem('products'));
    if (products) {
        document.querySelector('#detailproduct-a').textContent = '(' + localStorage.getItem('totalQuantity') + ')-' + localStorage.getItem('totalPrice') + 'Bs. Ordenar';
        document.getElementById('detailproduct').style.display = 'block';
    }
}

async function activeToListTypeProducts() {
    const tabsbox = document.querySelector('.tabs-box'),
        tabs = tabsbox.querySelectorAll('.tabs');
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabsbox.querySelector('.active').classList.remove('active');
            tab.classList.add('active')
        })
    })
}

async function registrarOrden() {

    let checkedSelect;
    const inputChecks = document.querySelectorAll('.form-check-input');
    inputChecks.forEach( check => {
            if (check.checked){
                checkedSelect = check;
            }
        }
    )

    let products = JSON.parse(localStorage.getItem('products'));
    const userData = JSON.parse(localStorage.getItem('userData'));
    let listaProductos = [];

    for (const clave in products) {
        listaProductos.push(products[clave]);
    }
    const totalAmount = Number(localStorage.getItem('totalPrice'));

    const orderSales = {
        totalAmount: totalAmount,
        lastUser: userData.user,
        typePayment: checkedSelect.id,
        details: listaProductos
    }

    const rawResponse = await fetch('registrarorden', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderSales)
    });
    const ordenSales = await rawResponse.json();

    console.log(ordenSales);

    if (ordenSales) {
        localStorage.setItem('orderSales', JSON.stringify(ordenSales));
        window.location.href = "/venta/notaventa";
    }
}

async function generarNotaVenta() {

    const products = JSON.parse(localStorage.getItem('products'));

    let litadoOrden = '';
    for (const clave in products) {
        if (clave !== null && clave !== undefined) {
            const valor = products[clave];
            let productHtml = '<tr>\n' +
                '<td>' + valor.quantity + '</td>\n' +
                '<td>' + valor.name + '</td>\n' +
                '<td>' + valor.price + '</td>\n' +
                '<td>' + (Number(valor.quantity) * Number(valor.price)) + '</td>\n' +
                '</tr>';
            litadoOrden += productHtml;
        }
    }
    document.querySelector('#resumentotalNotaVenta').textContent = 'Total: Bs. ' + localStorage.getItem('totalPrice');
    document.querySelector('#notaventa tbody').outerHTML = litadoOrden;

    const orderSales = JSON.parse(localStorage.getItem('orderSales'));

    // generar cabecera
    let htmlHeadOrderSales = '<div id="headersalesnote">\n' +
        '<h5>Nro. Ticket: ' + orderSales.ticketNumber + '</h5>\n' +
        '<h5>Fecha: ' + orderSales.dateRegister + ' Hrs.</h5>\n' +
        '<h5>Cajero: ' + orderSales.lastUser + '</h5></div>';

    document.querySelector('#headersalesnote').outerHTML = htmlHeadOrderSales;

}

async function limpiarLocalStorare() {
    localStorage.clear();
}

async function limpiarLocalStorareWithOutUser() {
    const userData = JSON.parse(localStorage.getItem('userData'));
    localStorage.clear();
    localStorage.setItem('userData', JSON.stringify(userData));
}

async function nuevaVenta() {
    limpiarLocalStorareWithOutUser()
    window.location.href = "/venta/";
}

async function listarReporte() {
    window.location.href = "/report/reportsales";
}

async function loadVenta(token) {
    const rawResponse = await fetch('venta/', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    });

    if (rawResponse.ok) {
        window.location.href = "/venta/";
        const html = await rawResponse.text(); // Obtener el HTML como texto
        document.open(); // Abrir el documento actual
        document.write(html); // Reemplazar el contenido con el nuevo HTML
        document.close(); // Finalizar la escritura
        history.pushState({}, '', 'venta/');
    }
}


// METODOS USER
document.getElementById('formlogin').addEventListener('submit', async function (event) {
    event.preventDefault(); // Evitar que se recargue la página
    signin();
});

async function signin() {

    const usuario = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const userData = {
        username: usuario,
        password: password,
    }

    const rawResponse = await fetch('login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });
    //console.log('ALLOBJ:',rawResponse);

    const responseToken = await rawResponse.json();

    console.log(responseToken);

    if (rawResponse.ok) {
        localStorage.setItem('userData', JSON.stringify(responseToken));
        window.location.href = "/venta/";
        //loadVenta(responseToken.token);
    }
}

async function loadVenta(token) {
    const rawResponse = await fetch('venta/', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });

    if (rawResponse.ok) {
        //window.location.href = "/venta/";
        const html = await rawResponse.text(); // Obtener el HTML como texto
        document.open(); // Abrir el documento actual
        document.write(html); // Reemplazar el contenido con el nuevo HTML
        document.close(); // Finalizar la escritura

        history.pushState({}, '', 'venta/');
    }
}

async function cargarMenuLeft() {
    const userData = JSON.parse(localStorage.getItem('userData'));
    if (userData) {
        console.log('OOOOOO:', userData);
        if (userData.role === '[ROLE_ADMIN]'){
            document.getElementById('limenureplace').outerHTML = '<li class="nav-item"><a class="nav-link" aria-current="page" href="/venta/">Venta</a></li>' +
                '<li class="nav-item"><a class="nav-link" aria-current="page" href="/product/products">Productos</a></li>' +
                '<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"aria-expanded="false">Reportes</a><ul class="dropdown-menu"><li><a class="dropdown-item" aria-current="page" href="/report/reportsales">Reporte de ventas</a></li><li><a class="dropdown-item" aria-current="page" href="/report/reportsalesprod">Control de ventas producto</a></li></ul></li>';
            document.getElementById('limenureplacerigth').outerHTML = '<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Perfil</a><ul class="dropdown-menu"><li><a class="dropdown-item" aria-current="page" href="/">Cerrar session</a></li></ul></li>';
        }

        if (userData.role === '[ROLE_CAJERO]'){
            document.getElementById('limenureplace').outerHTML = '<li class="nav-item"><a class="nav-link" aria-current="page" href="/venta/">Venta</a></li>';
            document.getElementById('limenureplacerigth').outerHTML = '<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Perfil</a><ul class="dropdown-menu"><li><a class="dropdown-item" aria-current="page" href="/">Cerrar session</a></li></ul></li>';
        }

        if (userData.role === 'ROLE_REPORTE'){
        }
    }else {
        document.getElementById('limenureplace').outerHTML = '<li id="limenureplace" ></li>';
        document.getElementById('limenureplacerigth').outerHTML = '<li id="limenureplacerigth" ><a class="nav-link active" aria-current="page" href="/">Ingreso</a></li>';
    }
}

async function exportarExcel() {
    const rawResponse = await fetch('exportexcel', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });

    console.log("RESPONSE:",rawResponse);
    if (rawResponse.ok) {

    }
}