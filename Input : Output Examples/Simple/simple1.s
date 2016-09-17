	.data
newline:	.asciiz "\n"
	.text
	.globl main
main:
	li $fp, 0x7ffffffc

B1:
	
	# loadI 0 => r_X
	li $t0, 0
	sw $t0, 0($fp)
	
	# readInt  => r_X
	li $v0, 5
	syscall 
	add $t0, $v0, $zero
	sw $t0, 0($fp)
	
	# loadI 2 => r0
	li $t0, 2
	sw $t0, -4($fp)
	
	# mult r0, r_X => r1
	lw $t1, -4($fp)
	lw $t2, 0($fp)
	mul $t0, $t1, $t2
	sw $t0, -8($fp)
	
	# writeInt r1 
	li $v0, 1
	lw $t1, -8($fp)
	add $a0, $t1, $zero
	syscall 
	li $v0, 4
	la $a0, newline
	syscall 
	
	# exit  
	li $v0, 10
	syscall 
