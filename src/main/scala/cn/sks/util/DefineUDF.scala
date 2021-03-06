package cn.sks.util

import java.util.regex.{Matcher, Pattern}
object DefineUDF {

  // 判断是否包含中文
  def isContainChinese(str:String): Boolean ={
    val pattern: Pattern = Pattern.compile("[\u4e00-\u9fa5]")
    val m: Matcher = pattern.matcher(str)
    if (m.find()) {
     return true
    }
     false
  }
  // 判断是否包含英文
  def isContainEnglish(str:String): Boolean ={
    val pattern: Pattern = Pattern.compile("[a-zA-z]+")
    val m: Matcher = pattern.matcher(str)
    if (m.find()) {
      return true
    }
     false
  }
  def isAllEnglish(str:String): Boolean ={
    str.matches("[a-zA-z]+")
  }
  def isAllChiness(str:String): Boolean ={
    str.matches("[\u4e00-\u9fa5]")
  }

  // 清洗 成果中 title 以及authors 中的 标签符号
  def clean_div(data:String): String ={
      if(data== null && data!="") null
      else {
          var htmlStr = data.replaceAll("&lt;","<")
        .replaceAll("&gt;",">")
        .replaceAll("&quot;","\"")
        .replaceAll("&amp;","&")
        .replaceAll("&nbsp;","")

          val script = "<script[^>]*?>[\\s\\S]*?<\\/script>" //定义script的正则表达式
          val style = "<style[^>]*?>[\\s\\S]*?<\\/style>" //定义style的正则表达式
          val html = "<[^>]+>" //定义HTML标签的正则表达式
          val p_script = Pattern.compile(script, Pattern.CASE_INSENSITIVE)
          val m_script = p_script.matcher(htmlStr)
          htmlStr = m_script.replaceAll("") //过滤script标签

          val p_style = Pattern.compile(style, Pattern.CASE_INSENSITIVE)
          val m_style = p_style.matcher(htmlStr)
          htmlStr = m_style.replaceAll("") //过滤style标签

          val p_html = Pattern.compile(html, Pattern.CASE_INSENSITIVE)
          val m_html = p_html.matcher(htmlStr)
          htmlStr = m_html.replaceAll("") //过滤html标签
            .replaceAll("#", "")
            .replaceAll("\\\\", "")
            .replaceAll("\\*", "") .replaceAll("\t", " ")
            .trim
          htmlStr
    }
  }

  //  清洗掉所有的特殊字符（融合时使用） 论文名字 单位
  def clean_fusion(data:String):String = {
    if (data == null) null
    else data.replaceAll("[^a-zA-Z0-9\u4E00-\u9FFF]","")
      .trim.toLowerCase()

  }

  // 统一成果中 authors 字段的分割符号
  def clean_separator(data:String):String = {
    if(data == null) null
      else {
      val dataStr = data.stripSuffix("|").stripSuffix(";").stripSuffix("；").stripSuffix("，").stripSuffix("、").stripSuffix("。").replaceAll("　"," ").replaceAll(" "," ").trim

      if (dataStr.contains("|")) dataStr.replace(";", "").replace("|", ";")
        .replace("。", "").replace(",", " ")
        .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ").replaceAll("  "," ")
        .stripSuffix(";")

      else if (dataStr.contains(";"))  dataStr.replace(",", " ").replace("。", "")
        .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ").replaceAll("  "," ")
        .stripSuffix(";")

      else if (dataStr.contains("；")) dataStr.replace("；", ";").replace("。", "").replace(",", " ")
        .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ").replaceAll("  "," ")
        .stripSuffix(";")

      else if (dataStr.contains("，"))
        dataStr.replace("，", ";").replace("。", "").replace(",", " ")
        .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ").replaceAll("  "," ")
        .stripSuffix(";")

      else if (dataStr.contains("、")) dataStr.replace("、", ";").replace("。", "") .replace(",", " ")
        .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ").replaceAll("  "," ")
       .stripSuffix(";")
      else if (dataStr.contains(",")) {
        if (dataStr.split(",").length==2 ) {
          if(dataStr.split(",")(0).trim.contains(" ") ||dataStr.split(",")(1).trim.contains(" ")) dataStr.replaceAll(",",";")
          else dataStr.replace(",", " ")
        }else{
          dataStr.replaceAll(",",";").replaceAll("  "," ")
            .replaceAll(" ;",";").replaceAll("; ",";").replace(";;", " ")
            .stripSuffix(";")
        }
      } else dataStr
    }
  }

  // 全角转半角
  def ToDBC(str:String):String={
    var charArray = str.toCharArray
    var ling = ""
    for (i<-0 until charArray.length){
      var t = charArray(i)
      if (t == '\u3000') {
        t == " "
      } else if (t > '\uFF00' && t< '\uFF5F') {
        t = (t-65248).toChar
      }
      ling +=t
    }
    ling
  }

}


