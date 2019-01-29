.class public bool_return

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



  invokestatic bool_return/main()I

  pop

  return



.end method



.method public static c(I)I
.limit locals 1000
.limit stack 1000
iconst_1
iload_0
iload_0
if_icmplt L0
pop
iconst_0
L0:
ireturn


return
.end method
.method public static main()I
.limit locals 1000
.limit stack 1000
iconst_0
invokestatic bool_return/c(I)I
ifeq L2
iconst_1
invokestatic Runtime/printInt(I)V


goto L1
L2:
iconst_0
invokestatic Runtime/printInt(I)V


L1:


iconst_0
ireturn


return
.end method
