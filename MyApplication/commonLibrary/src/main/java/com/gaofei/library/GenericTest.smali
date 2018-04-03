.class public Lcom/gaofei/library/GenericTest;
.super Ljava/lang/Object;
.source "GenericTest.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/gaofei/library/GenericTest$User;,
        Lcom/gaofei/library/GenericTest$Holder;
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static generic()V
    .registers 4

    .prologue
    .line 16
    new-instance v0, Lcom/gaofei/library/GenericTest$Holder;

    invoke-direct {v0}, Lcom/gaofei/library/GenericTest$Holder;-><init>()V

    .line 17
    .local v0, "holder":Lcom/gaofei/library/GenericTest$Holder;, "Lcom/gaofei/library/GenericTest$Holder<Lcom/gaofei/library/GenericTest$User;>;"
    new-instance v2, Lcom/gaofei/library/GenericTest$User;

    const-string v3, "gaoFei"

    invoke-direct {v2, v3}, Lcom/gaofei/library/GenericTest$User;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, v2}, Lcom/gaofei/library/GenericTest$Holder;->setTarget(Ljava/lang/Object;)V

    .line 18
    invoke-virtual {v0}, Lcom/gaofei/library/GenericTest$Holder;->getTarget()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/gaofei/library/GenericTest$User;

    .line 19
    .local v1, "user":Lcom/gaofei/library/GenericTest$User;
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v1}, Lcom/gaofei/library/GenericTest$User;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 20
    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 4
    .param p0, "args"    # [Ljava/lang/String;

    .prologue
    .line 6
    :try_start_0
    invoke-static {}, Lcom/gaofei/library/GenericTest;->generic()V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_3} :catch_4

    .line 12
    :goto_3
    return-void

    .line 7
    :catch_4
    move-exception v0

    .line 8
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 9
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v2, "error"

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 10
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v1, v0}, Ljava/io/PrintStream;->println(Ljava/lang/Object;)V

    goto :goto_3
.end method
