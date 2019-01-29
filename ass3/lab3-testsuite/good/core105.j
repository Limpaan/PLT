.class public core105

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



  invokestatic core105/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_5
istore_0


L0:
iconst_1
iload_0
iconst_3
if_icmpgt L2
pop
iconst_0
L2:
ifeq L1
iload_0
dup
iconst_1
isub
istore_0
invokestatic Runtime/printInt(I)V


goto L0
L1:
iload_0
ireturn


return
.end method
