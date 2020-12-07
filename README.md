# WEssential
轻量化的类Essential插件，作者Wickham
## 指令
### HOME
* 回到家的位置
```/home [家的名称]```

* 设置家的位置为当前位置
```/sethome [家的名称]```

* 查看所有家
```/homelist [家的名称]```

* 移除家
```/removehome [家的名称]```
## 配置文件及默认值
### config.yml
#### config_version:
配置文件版本号：```config_version: 1```

#### wessential_setting:
语言文件：```language_file: 'zh_cn.yml'```

#### game_rules:
是否关闭苦力怕爆炸破坏地形：  
```disable_creeper_explode_the_map: true```   

是否保护耕地不受踩踏：  
```protect_farmland: true```   

在所有世界强制开启死亡不掉落及不掉经验的游戏规则   
注意：开启后如需再次关闭这个功能，还需要在每个世界里操作一次游戏规则
```keep_inventory_in_all_world: false```


#### teleport_setting:
传送等待时间（秒）：```teleport_waiting_time: 5```

最大额外家  
除了默认的床位置可以 /home 返回，其他的家的数量  
```max_other_home: 1```
***
### zh_cn.yml

中文语言文件
***
### home.yml

存储了玩家的自定义家的位置

## 下载
### 正式版
本页面右边的 Releases 可以下载到正式版  
### 测试版
target 文件夹里有最新编译的 jar 文件