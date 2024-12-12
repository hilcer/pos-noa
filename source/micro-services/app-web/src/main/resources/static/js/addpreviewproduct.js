$(document).ready(function () {

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

document.addEventListener("DOMContentLoaded", () => {

    // limpiar localStorage luego del login
    if (window.location.pathname === "/login") {
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

    //activeToListTypeProducts();
    enabledButon()
});

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
    let products = JSON.parse(localStorage.getItem('products'));
    let listaProductos = [];

    for (const clave in products) {
        listaProductos.push(products[clave]);
    }
    const totalAmount = Number(localStorage.getItem('totalPrice'));

    const orderSales = {
        totalAmount: totalAmount,
        companyId: 2,
        sucursalId: 1,
        lastUser: "admin",
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

async function nuevaVenta() {
    limpiarLocalStorare();
    window.location.href = "/venta/";
}

