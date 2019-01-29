.class public redeclare_after_while

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



  invokestatic redeclare_after_while/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
istore_0


L0:
iconst_1
iload_0
dup
iconst_1
iadd
istore_0
ldc 10
if_icmplt L2
pop
iconst_0
L2:
ifeq L1
ldc 100
istore_1


goto L0
L1:
iload_0
istore_1


iload_1
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
