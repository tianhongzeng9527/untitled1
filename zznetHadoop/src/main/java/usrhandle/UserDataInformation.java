package usrhandle;

import org.json.JSONException;
import org.json.JSONObject;
import urlhandle.TitleKeyWords;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tian on 15-12-17.
 */
/*
1请求日期Datetime发起请求的时间,格式：20151217101232
2响应时间Datetime返回响应的时间,格式：20151217101232
3源IPString发起请求的客户端IP
4源端口String发起请求的客户端端口
5目的IPString服务器端IP
6目的端口String服务器端端口
7响应IPString返回响应到客户端的IP
8响应端口String返回响应到客户端端口
9客户端IPString接受响应的客户端IP
10客户端端口String接受响应的客户端端口
11请求方法String请求的方法类型，如GET
12URLString请求的URL
13User-AgentString用户的访问设备类型
14ReferURLString发起请求的上级URL
15CookieString发送请求时携带的Cookie集合，以Key=value形式
16域名String返回响应的
17URIString响应的URI18ContentString页面的HTML内容
 */
public class UserDataInformation {
    private String reqTime;
    private String respTime;
    private String reqIP;
    private String reqPort;
    private String destinationIP;
    private String destinationPost;
    private String respIP;
    private String respPort;
    private String acceptRespIP;
    private String acceptRespPost;
    private String reqMethod;
    private String reqURL;
    private String userEquipment;
    private String reqSupURL;
    private String reqCookie;
    private String domainName;
    private String htmlContent;
    private String schoolMessage;
    private List<String> keyWords;
    public boolean isNormalMessage;
    private final static int messageLength = 18;
    private final static int keyNum = 5;
    private Type type;
    private final static int sensitiveWordNum = -1;
    private TitleKeyWords titleKeyWords;
    private List<String> containsSensitiveWordsList;
    private Map<String, Integer> keyWordsType;
    private final static double similarScoreLine = -0.5;
    private String category;
    private final static int layer = 1;

    public UserDataInformation(String userDataInformation) throws IOException {
        String[] splits = userDataInformation.split(" ");
        category = "zaoyin";
        if (splits.length < messageLength) {
            isNormalMessage = false;
        } else {
            isNormalMessage = true;
            initUserInformation(splits);
            initKeyWords();
            containsSensitiveWordsList = titleKeyWords.getContainsSensitiveWordsList();
        }
    }

    private void initUserInformation(String[] splits) {
        reqTime = splits[0];
        respTime = splits[1];
        reqIP = splits[2];
        reqPort = splits[3];
        destinationIP = splits[4];
        destinationPost = splits[5];
        respIP = splits[6];
        respPort = splits[7];
        acceptRespIP = splits[8];
        acceptRespPost = splits[9];
        reqMethod = splits[10];
        reqURL = splits[11];
        userEquipment = splits[12];
        reqSupURL = splits[13];
        reqCookie = splits[14];
        domainName = splits[15];
        htmlContent = splits[16];
        schoolMessage = splits[17];
    }

    private void initKeyWords() throws IOException {
            String s = "<html><head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n" +
                    "<LINK href=\"http://www.pudn.com/inc/html.css\" type=text/css rel=stylesheet>\n" +
                    "<LINK href=\"http://www.pudn.com/style.css\" type=text/css rel=stylesheet>\n" +
                    "<title>Java-readability-master web 页面解析\n" +
                    "\n" +
                    "1. web 抓取\n" +
                    "\n" +
                    "2. html 正文提取 WEB(ASP,PHP,...) 238万源代码下载- www.pudn.com</title>\n" +
                    "<script language=\"JavaScript\">\n" +
                    "function checkinput()\n" +
                    "{\n" +
                    "\tif (document.form1.keyword.value==\"\")\n" +
                    "\t {\n" +
                    "\t\talert(\"请输入想查询的内容！\");\n" +
                    "\t\tdocument.form1.keyword.focus();\n" +
                    "\t\treturn false;\n" +
                    "\t }\n" +
                    "\t return true;\n" +
                    "}\n" +
                    "function openwin(keyword, se_type)\n" +
                    "{\n" +
                    "if(se_type =='pudn')\n" +
                    "  window.open('/search_db.asp?keyword=' + keyword, 'search');\n" +
                    "else\n" +
                    "  window.open('http://www.google.com/custom?domains=pudn.com&q=' + keyword +'&sitesearch=pudn.com&client=pub-8055710228382273&forid=1&ie=GB2312&oe=GB2312&safe=active&cof=GALT%3A%23008000%3BGL%3A1%3BDIV%3A%23336699%3BVLC%3A663399%3BAH%3Acenter%3BBGC%3AFFFFFF%3BLBGC%3A336699%3BALC%3A0000FF%3BLC%3A0000FF%3BT%3A000000%3BGFNT%3A0000FF%3BGIMP%3A0000FF%3BFORID%3A1&hl=zh-CN', 'search');\n" +
                    "}\n" +
                    "function favor_add(t, id, n)\n" +
                    "{\n" +
                    "  window.open('/favor_add.asp?e=' + id +'&t=' + t +'&n=' + n, '添加收藏');\n" +
                    "}\n" +
                    "function fcomment_q(id, grade)\n" +
                    "{\n" +
                    "  window.open('/fcomment_q.asp?id=' + id + '&grade=' + grade, '评论');\n" +
                    "}\n" +
                    "function SetIFrameHeight(iframe_id, add_y)\n" +
                    "{\n" +
                    "  var bobo=document.getElementById(iframe_id);\n" +
                    "   if (bobo && !window.opera)\n" +
                    "   {\n" +
                    "    if (bobo.contentDocument && bobo.contentDocument.body.offsetHeight){\n" +
                    "     bobo.height = bobo.contentDocument.body.offsetHeight+add_y;\n" +
                    "    }else if(bobo.Document && bobo.Document.body.scrollHeight){\n" +
                    "     bobo.height = bobo.Document.body.scrollHeight+add_y;\n" +
                    "    }\n" +
                    "   }\n" +
                    "}\n" +
                    "function addrow(tbobj,frm){\n" +
                    " var row = tbobj.insertRow(1);\n" +
                    " var col = row.insertCell(0);\n" +
                    " col.innerHTML = '请输入留言或评论：<input type=text name=content size=45> <input name=sbt type=submit value=提交>';\n" +
                    " frm.content.focus();\n" +
                    " return false;\n" +
                    "}\n" +
                    "var down_id=2529619;\n" +
                    "var type_id=107;\n" +
                    "var nav_info=\" &gt; <a href='/download1.html'>Downloads</a> &gt; <a href='/sourcecode/download2.html'>源码/资料</a> &gt; <a href='/sourcecode/internet/download25.html'>Internet/网络编程</a> &gt; <a href=/sourcecode/internet/web-design/download107.html>WEB(ASP,PHP,...)</a> &gt; <B>Java-readability-master</B>\";\n" +
                    "var user_email='';\n" +
                    "var en_url=\"http://en.pudn.com/downloads622/sourcecode/java/detail2529619_en.html\";\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "<body style='overflow-x:hidden;'>\n" +
                    "<div class=\"wrapper\">\n" +
                    "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/inc/detail.js\"></script>\n" +
                    "   <div class=\"pagebody2\">\n" +
                    "    <div id=\"detail_fname\">\n" +
                    "      &nbsp;文件名称: <B>Java-readability-master</B><a href=\"/dl.asp?id=2529619\" target=\"_blank\" rel=\"nofollow\" title=\"下载文件. 不支持迅雷等多线程工具\"><IMG src=\"http://www.pudn.com/images/d_download.gif\" border=0>下载</a>\n" +
                    "&nbsp; <a href=# onclick=\"javascript:favor_add('3', '2529619', 'Java-readability-master - web 页面解析');\" rel=\"nofollow\">收藏√</a>&nbsp; [<a href=# onclick=\"javascript:fcomment_q('2529619', '100');\" rel=\"nofollow\"><img src=http://www.pudn.com/images/thumbs_up.gif border=0 align=\"middle\" alt=\"投票：非常好\"></a>\n" +
                    "&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '100');\" title=\"投票：非常好\" rel=\"nofollow\">5</a> &nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '85');\" title=\"投票：还不错\" rel=\"nofollow\">4</a> &nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '75');\" title=\"投票：一般，勉强可用\" rel=\"nofollow\">3</a> &nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '50');\" title=\"投票：很一般\" rel=\"nofollow\">2</a> &nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '3');\" title=\"投票：太差了\" rel=\"nofollow\">1</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '3');\" rel=\"nofollow\"><img src=http://www.pudn.com/images/thumbs_down.gif border=0 align=\"middle\" alt=\"投票：太差了\"></a>]    </div>\n" +
                    "    <div id=\"detail_info\">\n" +
                    "      <div id=\"detail_gg\">\n" +
                    "<script language=\"JavaScript\">\n" +
                    "show_ad(\"ad_detail_b\");\n" +
                    "</script>\n" +
                    "      </div>\n" +
                    "      <div id=\"detail_list\">\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;所属分类: <a href=\"/sourcecode/internet/web-design/download107.html\"><B>WEB(ASP,PHP,...)</B></a>\n" +
                    "        </div>\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;开发工具: <B>Java</B>\n" +
                    "        </div>\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;文件大小: 34 KB\n" +
                    "        </div>\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;上传时间: 2014-05-04\n" +
                    "        </div>\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;下载次数: 3\n" +
                    "        </div>\n" +
                    "        <div class=\"detail_listnamevalue\">\n" +
                    "        &nbsp;&nbsp;提 供 者: <a href=http://s.pudn.com/upload_log.asp?e=4704448 target=_blank>冯永辉</a>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"detail_line\">\n" +
                    "&nbsp;详细说明：<B>web 页面解析\n" +
                    "\n" +
                    "1. web 页面抓取\n" +
                    "\n" +
                    "2. html 正文提取-html context extractor</B><div id=\"clogin\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"detail_line\">\n" +
                    "      <B>文件列表</B>(点击判断是否您需要的文件，如果是垃圾请在下面评价投诉): <BR>\n" +
                    "&nbsp;&nbsp;Java-readability-master<BR>&nbsp;&nbsp;.......................\\.gitignore<BR>&nbsp;&nbsp;.......................\\README<BR>&nbsp;&nbsp;.......................\\pom.xml<BR>&nbsp;&nbsp;.......................\\src<BR>&nbsp;&nbsp;.......................\\...\\main<BR>&nbsp;&nbsp;.......................\\...\\....\\java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\com<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\basistech<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\readability<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\AbstractPageReader.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\FilePageReader.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\HtmlPage.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\HttpPageReader.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\NekoJsoupParser.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\OffsetRange.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\PageCharsetDetector.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\PageInfo.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\PageLinkInfo.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\PageReadException.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\PageReader.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\Patterns.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\Readability.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\ReadabilityDriver.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\TikaCharsetDetector.java<BR>&nbsp;&nbsp;.......................\\...\\....\\....\\...\\.........\\...........\\XmlDataMap.java<BR>    </div>\n" +
                    "    <div id=\"detail_line\">\n" +
                    "<TABLE id=tb1 style=\"TABLE-LAYOUT: fixed; WORD-BREAK: break-all\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>\n" +
                    "<TBODY>\n" +
                    "<TR bgcolor=#F1F1F1>\n" +
                    "<TD>\n" +
                    "请<font color=red>评价</font>：<a href=# onclick=\"javascript:fcomment_q('2529619', '100');\" rel=\"nofollow\">推荐↑</a>\n" +
                    "&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '75');\" rel=\"nofollow\">一般</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '5');\" rel=\"nofollow\">有密码</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '40');\" rel=\"nofollow\">和说明不符</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '10');\" rel=\"nofollow\">不是源码或资料</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '20');\" rel=\"nofollow\">文件不全</a>\n" +
                    "&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '0');\" rel=\"nofollow\">不能解压</a>&nbsp;<a href=# onclick=\"javascript:fcomment_q('2529619', '3');\" rel=\"nofollow\">纯粹是垃圾</a>\n" +
                    "&nbsp;<a href=# onclick=\"javascript:addrow(tb1, addform); return false;\" rel=\"nofollow\">留言</a>\n" +
                    "</TD>\n" +
                    "</TR>\n" +
                    "<FORM action=/fcomment_q.asp method=get name=addform target=_blank>\n" +
                    "<input type=hidden name=id value=2529619>\n" +
                    "<TR>\n" +
                    "</FORM>\n" +
                    "<TD>\n" +
                    "</TD>\n" +
                    "</tr>\n" +
                    "<tr height=10><td></td></tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "    </div>\n" +
                    "<form name=frm_search2 action=/search_db.asp method=get>\n" +
                    "    <div class=\"detail_line\">\n" +
                    "&nbsp;输入关键字，在本站238万海量源码库中尽情搜索：<INPUT maxLength=50 size=20 name=keyword value=\"\">\n" +
                    "<input  type=submit value=\"搜 索\" name=_search2>&nbsp;<a href=/search_help.htm target=_blank>帮助</a>\n" +
                    "    </div>\n" +
                    "</form>\n" +
                    "    <div class=\"detail_line\">\n" +
                    "<script language=\"JavaScript\">\n" +
                    "show_ad('ad_detail_c');\n" +
                    "</script>\n" +
                    "<BR>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "<div id=\"rightSideBar\">\n" +
                    "<div class=\"dirList\">\n" +
                    "<TABLE cellSpacing=0 cellPadding=1 width=175 border=0 bgcolor=#F9F9F9>\n" +
                    "<TBODY>\n" +
                    "<TR bgColor=#D0D0D0><TD colSpan=2 height=1></TD></TR>\n" +
                    "<TR bgcolor=#F0F0F0><TD height=22 colspan=2><div class=malign><img src=http://www.pudn.com/images/folderopen.gif> WEB(ASP,PHP,...)</div></TD></TR>\n" +
                    "<TR bgColor=#D0D0D0><TD colSpan=2 height=1></TD></TR>\n" +
                    "<TR bgColor=#D0D0D0><TD colSpan=2 height=1></TD></TR>\n" +
                    "<TR bgcolor=#F0F0F0><TD height=22 colspan=2><div class=malign><img src=http://www.pudn.com/images/folderopen.gif> 相关类别</div></TD></TR>\n" +
                    "<TR bgColor=#D0D0D0><TD colSpan=2 height=1></TD></TR>\n" +
                    "</table>\n" +
                    "</div>\n" +
                    "<div class=\"sidebar\">\n" +
                    "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/inc/ad1.js\"></script>\n" +
                    "<p>\n" +
                    "·<A href=\"/downloads679/sourcecode/app/detail2744304.html\" target=_blank>考虑到会计发工资时一般都用Excel电</A><BR>\n" +
                    "·<A href=\"/downloads681/sourcecode/java/jsp/detail2750549.html\" target=_blank>二手网站交易系统的设计与实现 源码</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/web/detail2742981.html\" target=_blank>ftx飞天侠淘宝客源码6.0至尊版仿折8</A><BR>\n" +
                    "·<A href=\"/downloads681/sourcecode/others/detail2750559.html\" target=_blank>微信后台开发，二级菜单实例，非常</A><BR>\n" +
                    "·<A href=\"/downloads681/sourcecode/java/detail2750541.html\" target=_blank>kgMall介绍：\n" +
                    "\n" +
                    "kgMall多用户商城，</A><BR>\n" +
                    "·<A href=\"/downloads681/sourcecode/comm/android/detail2750435.html\" target=_blank>问卷调查可以自动生成试卷等，并且</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/web/detail2744560.html\" target=_blank>轩宇淘宝客是一款适用于淘宝客打折</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/math/detail2743944.html\" target=_blank>灰色理论认为系统的行为现象尽管是</A><BR>\n" +
                    "·<A href=\"/downloads682/sourcecode/web/detail2756148.html\" target=_blank>wemall微信商城适用于小企业电子商</A><BR>\n" +
                    "·<A href=\"/downloads680/sourcecode/java/javascript/detail2746179.html\" target=_blank>用JS实现和google地图一样的图片查</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/windows/csharp/detail2743760.html\" target=_blank>一个基于ASP.NET技术开发的网上人才</A><BR>\n" +
                    "·<A href=\"/downloads683/sourcecode/web/detail2758138.html\" target=_blank>最新US程序源码.net版之北京快乐8源</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/java/jsp/detail2743448.html\" target=_blank>在线网上选课系统，该系统由登陆模</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/windows/dotnet/detail2743637.html\" target=_blank>图片管理 以及 上传    的一个小程</A><BR>\n" +
                    "·<A href=\"/downloads680/sourcecode/java/javascript/detail2746313.html\" target=_blank>获取网站数据代码，大型列表网页数</A><BR>\n" +
                    "·<A href=\"/downloads680/sourcecode/web/detail2746360.html\" target=_blank>织梦文章自动生成二维码插件是一款</A><BR>\n" +
                    "·<A href=\"/downloads680/sourcecode/web/detail2746374.html\" target=_blank>读怪PHP小说程序是专为个人站长打造</A><BR>\n" +
                    "·<A href=\"/downloads679/sourcecode/web/detail2745485.html\" target=_blank>一个仿音悦台网站，包含注册登录，</A><BR>\n" +
                    "·<A href=\"/downloads681/sourcecode/web/detail2752987.html\" target=_blank>本在线代理深度优化以及汉化自Glype</A><BR>\n" +
                    "·<A href=\"/downloads682/sourcecode/web/detail2755071.html\" target=_blank>微信公众号平台类文件，可以用来便</A><BR>\n" +
                    "·<A href=\"/downloads23/sourcecode/unix_linux/network/detail73936.html\" target=_blank>400套超酷网站模板 有很多朋友做网</A><BR>\n" +
                    "·<A href=\"/downloads532/sourcecode/web/detail2201834.html\" target=_blank>仿淘宝天猫dedeCMS模板。红色大气，</A><BR>\n" +
                    "·<A href=\"/downloads85/sourcecode/app/detail327262.html\" target=_blank>asp.net 1.1编写的网上作业系统，为</A><BR>\n" +
                    "·<A href=\"/downloads134/ebook/detail570951.html\" target=_blank>高焕堂android中文书全,电子文件for</A><BR>\n" +
                    "·<A href=\"/downloads50/sourcecode/web/detail170382.html\" target=_blank>csdn三楼楼长提供的精品OA下载(包括</A><BR>\n" +
                    "·<A href=\"/downloads/sourcecode/internet/detail1437.html\" target=_blank>将HTML转换为TXT文本的小程序</A><BR>\n" +
                    "·<A href=\"/downloads72/sourcecode/web/detail261740.html\" target=_blank>一个学生成绩管理系统`用jsp+servle</A><BR>\n" +
                    "·<A href=\"/downloads80/sourcecode/others/detail309045.html\" target=_blank>tomcat 最新版本6.0.13 ，从Apache</A><BR>\n" +
                    "·<A href=\"/downloads87/sourcecode/internet/webserver/detail334259.html\" target=_blank>csdn三楼楼长提供的精品OA(包括完整</A><BR>\n" +
                    "·<A href=\"/downloads37/sourcecode/web/detail124184.html\" target=_blank>全国计算机等级考试在线注册系统,四</A><BR>\n" +
                    "·<A href=\"/downloads/sourcecode/internet/detail734.html\" target=_blank>利用IE接口分析HTML文件 </A><BR>\n" +
                    "·<A href=\"/downloads314/sourcecode/internet/webserver/detail1392484.html\" target=_blank>QQ钓鱼网站源码[高仿]上传后立即能</A><BR>\n" +
                    "·<A href=\"/downloads40/sourcecode/web/detail141026.html\" target=_blank>网上书店系统（包括后台管理）使用S</A><BR>\n" +
                    "·<A href=\"/downloads/sourcecode/internet/detail1356.html\" target=_blank>又一个将HTML文件转换成文本文件的</A><BR>\n" +
                    "·<A href=\"/downloads40/sourcecode/windows/network/detail136921.html\" target=_blank>这是一个完整功能齐全的企业网站ASP</A><BR>\n" +
                    "·<A href=\"/downloads71/sourcecode/others/detail259224.html\" target=_blank>数据库软件 php-5.2.1-Win32.zip</A><BR>\n" +
                    "·<A href=\"/downloads16/sourcecode/internet/webserver/detail60843.html\" target=_blank>一套整站在线电影网站系统</A><BR>\n" +
                    "·<A href=\"/downloads/sourcecode/database/detail1619.html\" target=_blank>站长写的使用OCI开发Oracle程序的通</A><BR>\n" +
                    "·<A href=\"/downloads90/sourcecode/others/detail343442.html\" target=_blank>Sun公司官方网站上下载的JDK文档，</A><BR>\n" +
                    "·<A href=\"/downloads76/sourcecode/web/detail286723.html\" target=_blank>毕业设计:新闻管理系统(ASP)+论文.</A><BR>\n" +
                    "</TBODY></TABLE>\n" +
                    "</p>\n" +
                    "</div></div>\n" +
                    "<div class=\"footer\" align=\"center\">\n" +
                    "<a href=mailto:programsalon@hotmail.com>联系站长</a> · <a href=/comment.asp?type_id=1010 target=_blank rel=\"nofollow\">版权投诉</a> · <a href=/comment.asp?type_id=1010 target=_blank rel=\"nofollow\">网站修改建议</a><br>\n" +
                    "</span>  &copy; 2004-2010 <a href=http://www.pudn.com><font color=red>pudn.com</font></a> 湘ICP备07000446<br>\n" +
                    "<script src=\"http://s117.cnzz.com/stat.php?id=1236358&web_id=1236358&show=pic\" language=\"JavaScript\" charset=\"gb2312\"></script>\n" +
                    "<script src=\"http://www.pudn.com/inc/tail.js\" language=\"JavaScript\"></script>\n" +
                    "</body>\n" +
                    "</html>\n";

        titleKeyWords = new TitleKeyWords(new URL("http://www.zhizhihu.com/html/y2013/4202.html"), keyNum);
        titleKeyWords.init();
        keyWords = titleKeyWords.getTopNumKey();
    }

    public void sensitiveClassify() {
        if (containsSensitiveWordsList.size() > sensitiveWordNum) {
            this.type = Type.sensitive;
        }
    }

    public void commonClassify() throws IOException, JSONException, InterruptedException {
        keyWordsType = new HashMap<String,Integer>();
        JSONObject categoryJsonObject = Category.category;
        for (int i = 0; i < layer; ) {
            for (String keyWord : keyWords) {
                double maxScore = 0;
                Iterator iterable = categoryJsonObject.keys();
                while (iterable.hasNext()) {
                    String tempCategory = (String) iterable.next();
                    double score = 0;
                    score = similarScore(keyWord, tempCategory);
                    if (score >= maxScore && score > similarScoreLine) {
                        category = tempCategory;
                        maxScore = score;
                    }
                }
                if (keyWordsType.containsKey(category))
                    keyWordsType.put(category, keyWordsType.get(category) + 1);
                else
                    keyWordsType.put(category, 1);
                int max = 0;
                for (Map.Entry<String, Integer> entry : keyWordsType.entrySet()) {
                    if (entry.getValue() > max) {
                        category = entry.getKey();
                        max = entry.getValue();
                    }
                }
                if (++i < layer)
                    try {
                        categoryJsonObject = categoryJsonObject.getJSONObject(category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private double similarScore(String word, String type) throws IOException, InterruptedException {
        double score = 0;
//        URL url = new URL("http://192.168.20.9:8080/wordsDistance?word1=" + word + "&word2=" + type);
//        URLConnection urlcon = url.openConnection();
//        InputStream is = urlcon.getInputStream();
//        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
//        String s = buffer.readLine();
//        score = Double.valueOf(s);
//        buffer.close();
        return score;
    }

    public String toString() {
        if (category != "zaoyin")
            return schoolMessage + " 1" + category;
        else if (this.type != Type.sensitive)
            return schoolMessage + " 2" + "zaoyin";
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : containsSensitiveWordsList) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }
            return "zaoyin" + reqIP + " " + stringBuilder + reqTime + category;
        }
    }

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        String s = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 数学家 tianhongzeng";
        UserDataInformation userDataInformation = new UserDataInformation(s);
        userDataInformation.sensitiveClassify();
        System.out.println(userDataInformation.similarScore("数学", "一部分"));
        userDataInformation.commonClassify();
        System.out.print(userDataInformation.toString());
    }
}
