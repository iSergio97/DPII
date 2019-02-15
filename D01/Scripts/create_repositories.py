# -*- coding: utf-8 -*-
"""
Éste script se usa para crear los convertidores.

Funciona leyendo los archivos "ActorToStringConverter.java" y
"StringToActorConverter.java" y creando copias cambiando la palabra "Actor" por
los nombres del resto de entidades de dominio tal y como se encuentran en la
variable "lista" declarada al principio del script.

Para usarlo, hay que poner este script en la carpeta converters con los archivos
"ActorToStringConverter.java" y "StringToActorConverter.java" y ejecutarlo.

@author: Javier Centeno Vega
"""

lista = ["AcmeFloat", "Administrator", "Area", "Brotherhood", "Finder", "IsRegistered", "Member", "Message", "MessageBox", "Procession", "Request", "SocialProfile", "SystemConfiguration", "UserAccount"]

for name in lista:
    template_to = open("ActorRepository.java", "r").read()
    template_to = template_to.replace("Actor", name)
    template_to = template_to.replace("actor", name[0:1].lower() + name[1:])
    open(name + "Repository.java", "w").write(template_to)
