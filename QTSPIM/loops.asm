# Sadhika Malladi - 11/1/14
# 
#
# Register Directory:
# $t0 current number

.text 0x00400000
.globl main

main:

# set $t0 to 1
li $t0,1

# print numbers 1-10
loop:
bgt $t0,10,exit
move $a0,$t0
li $v0,1
addu $t1,$t0,1
move $t0,$t1
syscall
j loop

exit:
li $v0,10
syscall
