//package com.code.web;
//
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequestMapping("echarts")
//public class EchartsController {
//
//    /*
//    * {
//          "month": ["1月","2月","3月","4月","5月","6月"],
//          "boys": [5, 200, 36, 10, 10, 200],
//          "girls": [200, 20, 360, 100, 10, 20]
//       }
//    sql：
//    select CONCAT(MONTH(create_time),'月') month ,count(id) count
//    from yx_user where sex='男' group by MONTH(create_time)
//
//    * 函数
//        month(create_time) ：取时间字段的月值
//        concat（值，”拼接的字符“）
//    * */
//    @RequestMapping("getUserRegirestData")
//    public HashMap<String, Object> getUserRegirestData(){
//
//        //数据查询 sql怎么写 数据封装
//        HashMap<String, Object> map = new HashMap<>();
//
//        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
//        map.put("boys",Arrays.asList(5, 200, 36, 10, 10, 200));
//        map.put("girls",Arrays.asList(200, 20, 360, 100, 10, 20));
//
//        /*
//        * http  应用层协议   短连接
//        * tcp/ip 网络层协议  长链接
//        * */
//        return map;
//    }
//
//
//    /*
//    [  List
//      {  UserVO
//        "sex": "小男生",
//        "city": [  list
//          {"name": "安徽","value": 230}, City
//          {"name": "河南","value": 230},
//          {"name": "山东","value": 200},
//          {"name": "云南","value": 500},
//          {"name": "河北","value": 700},
//          {"name": "山西","value": 200}
//        ]
//      },{
//        "sex": "小姑娘",
//        "city": [
//          {"name": "北京","value": 230},
//          {"name": "黑龙江","value": 230},
//          {"name": "辽宁","value": 200},
//          {"name": "陕西","value": 500},
//          {"name": "河南","value": 700},
//          {"name": "山东","value": 200}
//        ]
//      }
//    ]
//    * */
//    @RequestMapping("getUserRegirestChinaData")
//    public ArrayList<UserVO> getUserRegirestChinaData(){
//
//        //sql   select  * from yx_user where sex=‘男’ group by
//
//        ArrayList<City> boyCitys = new ArrayList<>();
//        boyCitys.add(new City("云南",230));
//        boyCitys.add(new City("河北",630));
//        boyCitys.add(new City("安徽",530));
//        boyCitys.add(new City("山东",730));
//        boyCitys.add(new City("河南",330));
//
//        ArrayList<City> girlsCitys = new ArrayList<>();
//        girlsCitys.add(new City("北京",230));
//        girlsCitys.add(new City("黑龙江",630));
//        girlsCitys.add(new City("辽宁",230));
//        girlsCitys.add(new City("陕西",530));
//        girlsCitys.add(new City("山东",430));
//
//        ArrayList<UserVO> userVOS = new ArrayList<>();
//        userVOS.add(new UserVO("小男生",boyCitys));
//        userVOS.add(new UserVO("小姑娘",girlsCitys));
//
//        return userVOS;
//    }
//
//    /*
//[  List
//  {  UserVO
//    "sex": "小男生",
//    "city": [  list
//      {"name": "安徽","value": 230}, City
//      {"name": "河南","value": 230},
//      {"name": "山东","value": 200},
//      {"name": "云南","value": 500},
//      {"name": "河北","value": 700},
//      {"name": "山西","value": 200}
//    ]
//  },{
//    "sex": "小姑娘",
//    "city": [
//      {"name": "北京","value": 230},
//      {"name": "黑龙江","value": 230},
//      {"name": "辽宁","value": 200},
//      {"name": "陕西","value": 500},
//      {"name": "河南","value": 700},
//      {"name": "山东","value": 200}
//    ]
//  }
//]
//* */
//
//    @Resource
//    UserService userService;
//
//    @RequestMapping("getUserRegirestChinaDatas")
//    public ArrayList<UserVO> getUserRegirestChinaDatas(){
//
//        List<City> boyCitys = userService.queryUserCity("男");
//        List<City> girlsCitys = userService.queryUserCity("女");
//
//        ArrayList<UserVO> userVOS = new ArrayList<>();
//        userVOS.add(new UserVO("小男生",boyCitys));
//        userVOS.add(new UserVO("小姑娘",girlsCitys));
//
//        return userVOS;
//    }
//
//}
