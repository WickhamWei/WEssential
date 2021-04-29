# WEssential

轻量化的类Essential插件，作者 Wickham阿华。

### 意见与建议

如果有想要的功能、想改进的功能或者待修复的Bug，请随意向此项目发起Issue。

## 功能及指令

### WLogin 登录系统

* 注册
  ```/register <密码> <密码>```

* 登录
  ```/login <密码>```

* 修改密码
  ```/changePassword <密码> <密码>```

### WTeleport 传送系统

* 回到家的位置
  ```/home [家的名称]```

* 设置家的位置为当前位置
  ```/sethome [家的名称]```

* 查看所有家
  ```/homelist```

* 移除家
  ```/removehome <家的名称>```

* 请求传送到某人
  ```/tpa <玩家名称>```

* 接受某人的传送请求
  ```/tpaccept```

* 回到上一个位置
  ```/back```

* 传送所有人到身边（OP）
  ```/tpall```

### WProtect 资产保护系统

#### 箱子锁

* 使用橡木告示牌左键即可锁上箱子
* 使用橡木告示牌左键自己锁上的箱子可以选中箱子
* 选中箱子后可以查看其他用户列表
* 选中箱子后可以给箱子添加其他用户
  ```/add <玩家名称> [玩家名称]...```

* 使用以上指令可以同时添加多个用户
* 箱子的额外用户只能打开箱子，不能破坏箱子及木牌

#### 领地锁

* OP在创造模式下可设置领地
* 手持木棍，使用鼠标左右键选中两个点
* 使用指令将以两点为对角线的立方体设为领地
  ```/setres <领地名称>```

* 领地内，其他人无法破坏，无法纵火。箱子可打开，仍需箱子锁。
* 获取你的所有领地```/reslist```
* 删除领地```/removeres <领地名称>```

### WMinors 未成年保护系统

* 被设置为未成年的玩家在 8 点前或 22 点后无法登录

* 设置未成年
  ```/setminors <玩家名称> [玩家名称]...```

* 玩家是否被设置为未成年
  ```/isminors <玩家名称>```

* 查看被设置为未成年的玩家列表
  ```/minorslist```

* 取消设置玩家为未成年
  ```/removeminors <玩家名称> [玩家名称]...```

## 配置文件及默认值

### config.yml

#### config_version:

* 配置文件版本号：```config_version: 1```

#### wessential_setting:

* 语言文件：```language_file: 'zh_cn.yml'```

#### login_setting:

* 启用登录系统：```enable_login_system: true```

#### game_rules:

* 是否关闭苦力怕爆炸破坏地形：```disable_creeper_explode_the_map: true```

* 是否保护耕地不受踩踏：```protect_farmland: true```

* 在所有世界强制开启死亡不掉落及不掉经验的游戏规则   
  注意：开启后如需关闭这个功能，还需要在每个世界里关闭一次此游戏规则  
  ```keep_inventory_in_all_world: false```

#### teleport_setting:

* 传送等待时间（秒）：```teleport_waiting_time: 5```

* 传送冷却时间（秒）：```teleport_cooling_time: 5```

* 最大额外家  
  除了默认的床位置可以 /home 返回，其他的家的数量：```max_other_home: 1```

#### minors_setting:

* 启用脏话屏蔽：```enable_shield: true```

***

### zh_cn.yml

中文语言文件
***

### home.yml

存储了玩家的自定义家的位置

***

### back.yml

存储了玩家传送之前的位置

***

### password.yml

存储了玩家加密之后的密码和IP地址

***

### chest.yml

存储了玩家锁上的箱子

***

### minors.yml

存储了未成年玩家列表

***

### residence.yml

存储了领地

## 下载

### 正式版

本页面右边或下面的 Releases 可以下载到正式版

### 测试版

target 文件夹里有最新编译的 jar 文件  
建议每次更新测试版时都删除所有 yml 文件重新生成