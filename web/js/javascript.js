/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function clickListener(e) {
    var cpf = e.getAttribute(cpf);
    if (cpf.trim() != "") {
        alert("não pegou!!");
    }else{
        alert("o valor pego é: " + cpf);
    }
    
  /*Uma vez que você pegou o id você pode mudar o fundo mandar o conteúdo da linha por ajax para fazer algo no banco etc...*/
}