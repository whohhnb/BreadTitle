### 介绍一下

让你的玩家吃掉它获得称号吧！通过Luckperms API来添加删除玩家的前缀！\
支持修改Lore和Name

### 配置文件

```
bread:
  name: '&e神奇的谜之炖菜'
  lore:
  - '&7铁砧里修改名字'
  - '&7吃掉后获得称号'
```

### 兼容性

 兼容 Minecraft 1.20以上的所有版本，不支持Folia\
 需要前置 LuckPerms 5.4以上！

### 权限

```
  breadtitle.color:
    default: false
    description: 允许使用颜色代码
  breadtitle.admin:
    default: op
    children:
      breadtitle.color: true
```
### 命令

`/brt` （主命令）\
`/brt give [玩家]` （给予玩家称号物品）\
`/brt reload` （重载配置文件）\
`/brt name` （跳过铁砧步骤，直接改名）
