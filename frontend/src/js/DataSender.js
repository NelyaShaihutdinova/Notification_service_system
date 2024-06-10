import {checkInputLogIn, checkInputSignUp} from "./InputValidator";
import {createAccountPage, createCorrectInput} from "../index";

export async function sendRequestLogIn(body) {
    const response = await fetch("/auth-api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
        body: JSON.stringify({
            password: body.password,
            email: body.email,
        }),
    })
    if (!(response.ok)) {
        checkInputLogIn(response.json());
    } else {
        createCorrectInput("appPasswordLogIn");
        createCorrectInput("appEmailLogIn");
        localStorage.setItem('data', JSON.stringify(response));
        window.location.href = '/account';
        return JSON.stringify(response);
    }
}

export async function sendRequestSignUp(body) {
    fetch("/auth-api/registration", {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
        body: JSON.stringify({
            username: body.username,
            password: body.password,
            email: body.email,
        }),
    }).then((response) => {
        console.log(`Registration response from auth: ${response.status}`);
        console.log(response);
        if (!(response.ok)) {
            checkInputSignUp(response.json());
        } else {
            createCorrectInput("appPasswordSignUp");
            createCorrectInput("appMailSignUp");
            createCorrectInput("appNameSignUp");
            window.open("/checkMailPage");
        }
    });
}

