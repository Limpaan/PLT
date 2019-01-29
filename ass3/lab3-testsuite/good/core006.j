.class public core006

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



  invokestatic core006/main()I

  pop

  return



.end method



.method public static main()I
.limit locals 1000
.limit stack 1000


ldc 45
dup
istore_0
pop


ldc 36
ldc 67
iadd
dup
istore_1
pop


iload_0
invokestatic Runtime/printInt(I)V


iload_1
invokestatic Runtime/printInt(I)V


iconst_0
ireturn


return
.end method
