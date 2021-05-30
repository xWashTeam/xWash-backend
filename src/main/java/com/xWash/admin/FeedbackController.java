package com.xWash.admin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.dao.FeedbackDao;
import com.xWash.entity.APIResult;
import com.xWash.entity.Feedback;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * 反馈操作控制器
 * |- 读反馈
 * |- 接受反馈
 *   |- 识别反馈
 *   |- 清洗反馈
 * |- 拒绝非法反馈
 */

@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    Logger logger = Logger.getLogger(LogManager.ROOT_LOGGER_NAME);
    int maxLength = 255;

    public String pureText(String text){
        return text.replace("union","[unionnn]").replace("select","[selecttt]");
    }

    @RequestMapping(path = "/",method = RequestMethod.POST)
    @ResponseBody
    public APIResult receiveFeedback(HttpServletRequest request, HttpServletResponse response
            , @RequestBody String jsonData){
        JSONObject json = JSONUtil.parseObj(jsonData);
        String text = (String) json.get("text");
        String name = (String) json.get("name");
        // 1.过滤非法文本
        String clearText = pureText(text);
        if(clearText.length()>=maxLength){
            return APIResult.createWithMsg(400,"文本长度过长~");
        }

        // 2. 检验name是否填了
        if(name==null|| name.equals("")||name.equals("undefined")){
            name = "热心人士";
        }

        // 3. 写入feedback
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        FeedbackDao feedbackMapper = (FeedbackDao)context.getBean("feedbackMapper");
        Feedback fb = new Feedback();
        fb.setName(name);
        fb.setText(clearText);
        boolean flag = feedbackMapper.saveFeedback(fb);


        // 4.判断
        if(flag){
            return APIResult.createWithMsg(200,"已经收到您的反馈! 感谢反馈! ");
        }
        return APIResult.createWithMsg(500,"反馈失败了, 请查看反馈内容中是否含有非法代码内容, 期待您的下次反馈. 若始终无法反馈, 请麻烦您通过邮箱联系我.");

    }

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public void test(){
       try {
           logger.info("test ready to sleep ");
           Thread.sleep(1000*5);
           logger.info("test awake");
       }catch (Exception e){
           e.printStackTrace();
       }
    }
//
//    @RequestMapping(path = "/",method = RequestMethod.GET)
//    public void getAllFeedback(){
//
//    }
//    @RequestMapping(path = "/{no}",method = RequestMethod.GET)
//    public void getOneFeedback(@PathVariable(name = "no")String no){
//
//    }
//
//    @RequestMapping(path = "/",method = RequestMethod.PUT)
//    public void updateFeedback(String no){
//
//    }
//    @RequestMapping(path = "/",method = RequestMethod.DELETE)
//    public void deleteFeedback(int no){
//
//    }



}
