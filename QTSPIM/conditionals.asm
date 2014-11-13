# Sadhika Malladi - 11/11/14
# Takes in two numbers from user and outputs the greater one
#
# Register Directory:
# $t0 first number
# $t1 second number

.text 0x00400000
.globl main

main:

# read in input and store in registers
li $v0,5 # read from user input
syscall
move $t0,$v0 # save read input to t0 register
li $v0,5
syscall
move $t1,$v0

# check if $t1 is greater than $t0 and 
# execute print accordingly
bgt $t1,$t0,printSecondNum
j printFirstNum

printSecondNum:
move $a0, $t1
li $v0,1
syscall
j exit

printFirstNum:
move $a0, $t0
li $v0,1
syscall

exit:
li $v0,10
syscall

