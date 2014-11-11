# Sadhika Malladi - 11/11/14
# Computes the difference between two numbers that the user inputs at runtime.
# Register Directory:
# $t0 first number
# $t1 second number
# $t3 the difference (first number - second number)

main:
li $v0,5 # read from user input
syscall
move $t0,$v0 # save read input to t0 register
li $v0,5
syscall
move $t1,$v0

subu $t3,$t0,$t1 # $t3 = $t0 - $t1

# print out $t3
move $a0, $t3
li $v0,1
syscall

# exit
li $v0,10
syscall


