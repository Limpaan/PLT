.class public fibonacci

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



  invokestatic fibonacci/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


iconst_1
dup
istore_0
pop


iload_0
dup
istore_1
pop


ldc 5000000
dup
istore_2
pop


iload_0
invokestatic Runtime/printInt(I)V


L0:
iconst_1
iload_1
iload_2
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
iload_1
invokestatic Runtime/printInt(I)V


iload_0
iload_1
iadd
dup
istore_1
pop


iload_1
iload_0
isub
dup
istore_0
pop




goto L0
L1:
iconst_0
ireturn


return
.end method
