.class public large_program_fac

.super java/lang/Object



.method public <init>()V

  .limit locals 1



  aload_0

  invokespecial java/lang/Object/<init>()V

  return



.end method



.method public static main([Ljava/lang/String;)V

  .limit locals 1

  .limit stack  1



  invokestatic large_program_fac/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
ldc 10
invokestatic large_program_fac/fac(I)I
invokestatic Runtime/printInt(I)V


ldc 10
invokestatic large_program_fac/rfac(I)I
invokestatic Runtime/printInt(I)V


ldc 10
invokestatic large_program_fac/mfac(I)I
invokestatic Runtime/printInt(I)V


ldc 10
invokestatic large_program_fac/ifac(I)I
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
.method public static fac(I)I
.limit locals 1000
.limit stack 1000




iconst_1
dup
istore_1
pop


iload_0
dup
istore_2
pop


L0:
iconst_1
iload_2
iconst_0
if_icmpgt L2
pop
iconst_0
L2:
ifeq L1
iload_1
iload_2
imul
dup
istore_1
pop


iload_2
iconst_1
isub
dup
istore_2
pop




goto L0
L1:
iload_1
ireturn


return
.end method
.method public static rfac(I)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
iconst_0
if_icmpeq L5
pop
iconst_0
L5:
ifeq L4
iconst_1
dup
istore_1
pop


goto L3
L4:
iload_0
iload_0
iconst_1
isub
invokestatic large_program_fac/rfac(I)I
imul
dup
istore_1
pop


L3:


iload_1
ireturn


return
.end method
.method public static mfac(I)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
iconst_0
if_icmpeq L8
pop
iconst_0
L8:
ifeq L7
iconst_1
dup
istore_1
pop


goto L6
L7:
iload_0
iload_0
iconst_1
isub
invokestatic large_program_fac/nfac(I)I
imul
dup
istore_1
pop


L6:


iload_1
ireturn


return
.end method
.method public static nfac(I)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
iconst_0
if_icmpne L11
pop
iconst_0
L11:
ifeq L10
iload_0
iconst_1
isub
invokestatic large_program_fac/mfac(I)I
iload_0
imul
dup
istore_1
pop


goto L9
L10:
iconst_1
dup
istore_1
pop


L9:


iload_1
ireturn


return
.end method
.method public static ifac(I)I
.limit locals 1000
.limit stack 1000
iconst_1
iload_0
invokestatic large_program_fac/ifac2f(II)I
ireturn


return
.end method
.method public static ifac2f(II)I
.limit locals 1000
.limit stack 1000


iconst_1
iload_0
iload_1
if_icmpeq L14
pop
iconst_0
L14:
ifeq L13
iload_0
dup
istore_2
pop


goto L12
L13:
iconst_1
iload_0
iload_1
if_icmpgt L17
pop
iconst_0
L17:
ifeq L16
iconst_1
dup
istore_2
pop


goto L15
L16:


iload_0
iload_1
iadd
iconst_2
idiv
dup
istore_3
pop


iload_0
iload_3
invokestatic large_program_fac/ifac2f(II)I
iload_3
iconst_1
iadd
iload_1
invokestatic large_program_fac/ifac2f(II)I
imul
dup
istore_2
pop




L15:


L12:


iload_2
ireturn


return
.end method
