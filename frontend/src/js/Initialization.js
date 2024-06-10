import {sendRequestSignUp} from "./DataSender.js";

import {sendRequestLogIn} from "./DataSender.js";

export async function getInputRegistration() {
  const username2 = document.querySelector("#username2").value;
  const email = document.querySelector("#mail").value;
  const password2 = document.querySelector("#password2").value;
  const body2 = {
    username: username2,
    email: email,
    password: password2,
  };
  const data = await sendRequestSignUp(body2);
  console.log(data)

}

export function getInputLogIn(){
  const email1 = document.querySelector("#email1").value;
  const password1 = document.querySelector("#password1").value;
  const body1 = {
    email: email1,
    password: password1,
  };
  const data = sendRequestLogIn(body1);
  console.log(data);
}