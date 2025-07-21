# 🛒 Sistema de PDV em linha de comando

Essa é uma aplicação simples que simula a execução de um sistema PDV (Ponto De Venda) de um Super Mercado no terminal. O sistema conta com Frente de Caixa e ferramentas de gerenciamento para o usuário.

> ⚙️ Em construção... 

## 📌 Features
- [x] 💵 Sistema de Caixa
- [x] 📦 Controle de estoque 
- [x] 💿 Persistência em Banco de Dados

## 💵 Como usar: Frente de Caixa

### # Funções
| entrada | função                  |
|---------|-------------------------|
| f       | Finalizar compra / Sair |
| c       | Cancelar item           |

### # Exemplo de uso
```
>>>>>> CASHIER <<<<<<
>>> 445
+-----------------------------------------------------------------------------------------+
|       | codigo          | nome                 |        preco |      qtd |        total |
+-----------------------------------------------------------------------------------------+
| #1    | 445             | Melancia             | R$      9.98 |    1 uni | R$      9.98 |
+-----------------------------------------------------------------------------------------+
| Melancia             R$9.98           1 uni |
+---------------------------------------------+
| SUBTOTAL = R$9.98       |
+-------------------------+
>> f

>> Tecle ENTER para voltar <<
Qual a forma de pagamento?
   [0] Dinheiro
   [1] Débito
   [2] Crédito
   [3] Pix
   [4] Crédito manual
   [5] Boleto
   [6] Vale troca
>> 0
>> Valor de entrada: 10.00

AGUARDE...

TROCO = R$0.02
AGUARDE...
>> Sessão finalizada <<
```
