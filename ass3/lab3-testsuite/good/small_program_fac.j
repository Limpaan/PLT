.class public small_program_fac

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



  invokestatic small_program_fac/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_5
invokestatic small_program_fac/fac(I)I
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
