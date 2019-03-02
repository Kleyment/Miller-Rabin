# Miller-Rabin

Développeurs

* Valentin THOUVENIN
* Clément BELLANGER

## Prérequis

* Java 8
* Ant

## Compilation

```ant``` ou ```ant jar```

## Exécution

```cd jar-ant```

### Options possibles

* ```-testDecomp <valeur>```
* ```-testExpMod <valeur>```
* ```-testEval <nombre de valeurs> <nombre de bits> <nombre d'itérations de miller-rabin>```
* ```-mr <nombre en hexa> <nombre d'itérations>```
* ```-eval <nombre de bits> <nombre d'itérations de miller-rabin>```

### Exemple - Miller-Rabin

Pour lancer le test de Miller-Rabin 20 fois sur le nombre 13 (donc 0x0D en hexadécimal).

```java -jar Miller-Rabin.jar -mr D 20```

### Exemple - Une évaluation

Nombre de génération aléatoire de nombre de 1024 bits qu'il a fallu pour obtenir un nombre probablement premier (avec 20 itérations de Miller-Rabin)

```java -jar Miller-Rabin.jar -eval 1024 20```


### Exemple - Test d'exponentiation modulaire

Lancement de 10 000 tests d'exponentiations modulaires avec des valeurs de n, t et a sur 1024 bits avec redirection dans un fichier.

```java -jar Miller-Rabin.jar -testExpMod 10000```

### Exemple - Test de décomposition de nombres sous la forme n-1=2^s*d

Lancement de 10 000 tests de décomposition sur des entiers de 1024 bits avec redirection dans un fichier.

```java -jar Miller-Rabin.jar -testDecomp 10000```

### Exemple - Test d'évalutation

Nombre moyen de nombres de générations aléatoires de nombres de 1024 bits qu'il a fallu pour obtenir un nombre probablement premier (avec 20 itérations de Miller-Rabin).

(Moyenne sur 100).

```java -jar Miller-Rabin.jar -testEval 100 1024 20```