	.data
newline:	.asciiz "\n"
	.text
	.globl main
main:
	li $fp, 0x7ffffffc

B1:
	
	# loadI 0 => r_N
	li $t0, 0
	sw $t0, 0($fp)
	
	# loadI 0 => r_SQRT
	li $t0, 0
	sw $t0, -4($fp)
	
	# readInt  => r_N
	li $v0, 5
	syscall 
	add $t0, $v0, $zero
	sw $t0, 0($fp)
	
	# loadI 0 => r0
	li $t0, 0
	sw $t0, -8($fp)
	
	# i2i r0 => r_SQRT
	lw $t1, -8($fp)
	add $t0, $t1, $zero
	sw $t0, -4($fp)
	
	# jumpI  -> B2
	j B2

B2:
	
	# mult r_SQRT, r_SQRT => r1
	lw $t1, -4($fp)
	lw $t2, -4($fp)
	mul $t0, $t1, $t2
	sw $t0, -12($fp)
	
	# cmp_LE r1, r_N => r2
	lw $t1, -12($fp)
	lw $t2, 0($fp)
	sle $t0, $t1, $t2
	sw $t0, -16($fp)
	
	# cbr r2 -> B3, B4
	lw $t0, -16($fp)
	bne $t0, $zero, B3

L11:
	j B4

B3:
	
	# loadI 1 => r3
	li $t0, 1
	sw $t0, -20($fp)
	
	# add r_SQRT, r3 => r4
	lw $t1, -4($fp)
	lw $t2, -20($fp)
	addu $t0, $t1, $t2
	sw $t0, -24($fp)
	
	# i2i r4 => r_SQRT
	lw $t1, -24($fp)
	add $t0, $t1, $zero
	sw $t0, -4($fp)
	
	# jumpI  -> B2
	j B2

B4:
	
	# loadI 1 => r5
	li $t0, 1
	sw $t0, -28($fp)
	
	# sub r_SQRT, r5 => r6
	lw $t1, -4($fp)
	lw $t2, -28($fp)
	subu $t0, $t1, $t2
	sw $t0, -32($fp)
	
	# i2i r6 => r_SQRT
	lw $t1, -32($fp)
	add $t0, $t1, $zero
	sw $t0, -4($fp)
	
	# writeInt r_SQRT 
	li $v0, 1
	lw $t1, -4($fp)
	add $a0, $t1, $zero
	syscall 
	li $v0, 4
	la $a0, newline
	syscall 
	
	# exit  
	li $v0, 10
	syscall 
