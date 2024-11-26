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

    const product = {productId: productId, name: name, image: image , price: price, quantity: 1};

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
    console.log('producto :' + products)
    document.getElementById('detailproduct').style.display = 'block';

    totalQuantity = Number(localStorage.getItem('totalQuantity')) + 1;
    localStorage.setItem('totalQuantity', totalQuantity.toString());
    document.querySelector('#detailproduct-a').textContent = '(' + totalQuantity + ')-' + totalPrice + 'Bs. Pagar';
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
                document.querySelector('#detailproduct-a').textContent = '(' + totalQuantity + ')-' + totalPrice + 'Bs. Pagar';

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
        const valor = products[clave];

        let productHtml = '<tr>\n' +
        '<td><img th:src="/img/product_img/'+ valor.image +'" width="50px" height="50px"></td>\n' +
        '<td>'+ valor.name + '</td>\n' +
        '<td> Bs'+ valor.price + '</td>\n' +
        '<td>'+ valor.quantity + '</td>\n' +
        '<td>\n' +
        '<a onclick="quitarProducto(this)" class="btn btn-sm btn-danger"> - </a><a onclick="agregarProducto(this)" class="btn btn-sm btn-success"> + </a>\n' +
        '</td></tr>';

        litadoOrden += productHtml;
    }
    document.querySelector('#preorden tbody').outerHTML = litadoOrden;
}

document.addEventListener("DOMContentLoaded", () => {
    // Verificar la URL actual
    if (window.location.pathname === "/venta/orden") {
        // Ejecutar código específico
        generarPreOrden();
    }
});

async function llamadaPos(){
    let listaProductos = [];
    for (const clave in products) {
        listaProductos.push(products[clave]);
    }



    const rawResponse = await fetch('venta/preorden', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(listaProductos)
    });
    const content = await rawResponse.json();

    console.log(content);
}


async function agregarProductoX() {
    const rawResponse = await fetch('http://localhost:8080', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const content = await rawResponse.json();

    console.log(content);
}

