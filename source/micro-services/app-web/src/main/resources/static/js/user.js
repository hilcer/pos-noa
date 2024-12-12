

async function signin(){

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
    const responseToken = await rawResponse.json();

    console.log(responseToken);

    if (responseToken) {
        localStorage.setItem('userData', JSON.stringify(responseToken));
        window.location.href = "/venta/";
    }
}

