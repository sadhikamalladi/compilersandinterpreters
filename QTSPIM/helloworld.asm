.text 0x00400000
.globl main 
main: 
li $v0, 4 
la $a0, msg 
syscall 
li $v0, 10 
syscall 
.data 
msg: 
.asciiz "Hello World!\n" 
