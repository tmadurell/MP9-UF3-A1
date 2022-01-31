## UDP

>**Tasca 1**  
>Implementeu el codi d'exemple que hi ha als apunts (Servidor i CLient), i hi afegiu el següent:  
>Feu que:
> - El client hagi d'entrar el seu nom en la primera connexió amb el servidor
> - Que cada cosa que el client li envia al servidor, el servidor ho printi per consola
> - Si el client li envia un "adeu" al servidor, el client es desconnecta, però no pas el servidor
> - Que el servidor envii com a resposta al client el mateix que el client li ha enviat però en majúscules
    *Per tal de discrimar el bytes restants del packet i poder obtenir el missatge correcte, feu servir el següent*
```java
    String msg = new String(data,0,lenght);
    //data és l'array de bytes rebuts
```
<hr>

>**Tasca 2**  
> Seguint l'esquema après a la tasca 1, crea un servidor que generi un número aleatori(fes-ho amb la classe 'SecretNum.java') i atengui
> a les peticions dels clients que intenten encertar aquest número.
> Ves contabilitzant els intents que cada client/jugador fà.
>
> El servidor un cop rebut el numero proposat per el client li ha de respondre si el número que té és més gran, més petit
> del que li ha enviat, o si ha encertat. Usar només el codi numèric de SecretNum.java (0,1,2)

Per transformar in 'int' en un 'byte[]' apliqueu el següent:
```java
int nombre = ByteBuffer.wrap(data).getInt(); //data és l'array de bytes
byte[] resposta = ByteBuffer.allocate(4).putInt(num).array(); //num és un int
```

>**Tasca 3 (Multicast)**  
> Crea un servidor multicast que emeti paraules aleatòries agafades d'una llista.  
> Crear un client que es connecti a aquest servei multicast i imprimeixi les paraules  
> amb el número de vegades que han sortit o van sortint.
>**Tasca 4 (Multicast)**  
> Crea un servidor multicast que emeti frases aleatòries agafades d'una llista
> Crear un client que es connecti a aquest servei multicast i imprimeixi les frases continguin més de 8 paraules.