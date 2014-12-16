#!/bin/bash

jflex calc.flex
byaccj -Jpackage=gen.mat.parse calc.y
rm -f *~
mv *.java ../gen/mat/parse/
