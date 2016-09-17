             .data
newline:     .asciiz "\n"
             .text
             .globl main
main:
             li $fp, 0x7ffffffc
             li $t0, 0
             sw $t0, -4($fp)
             lw $t0, -4($fp)
             addu $t1, $t0, $fp
             sw $t1, -8($fp)
             li $v0,5
             syscall
             move $t0, $v0
             sw $t0, -8($fp)
             li $t0, 2
             sw $t0, -12($fp)
             lw $t0, -12($fp)
             lw $t1, -8($fp)
             mul $t2, $t0, $t1
             sw $t2, -16($fp)
             lw $t2, -16($fp)
             move $a0, $t2
             li $v0, 1
             syscall
             j Bexit 

Bexit:       li $a0, 10
             li $v0, 10
             syscall
