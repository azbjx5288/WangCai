(function($){
//使用直选复式类
    $.BallData = function (ball) {
         if (typeof ball == "undefined") {
             console.log("select ball data is null");
             return;
         }
         var data = {"ball":ball};
         switch (androidJs.getSeriesId()) {
            case 4://快三
                return { singleNum: kuaisan.planNum(data) };
            default:
                return { singleNum: planNum(data) };
         }
    };

    //快三彩系：苹果快三分分彩、江苏快三、甘肃快三、福建快三、广西快三、安徽快三、河北快三
    var kuaisan = {
        planNum: function(balldata) {
            switch (androidJs.getMethodName() + androidJs.getMethodId()) {
                case "dx160":
                    return kuaisan.dx160(balldata);
                case "fs162":
                case "cbc3377":
                    return getFirstLineCombine(balldata, 3);
                case "fs163":
                case "cbc2376":
                    return getFirstLineCombine(balldata, 2);
                default:
                    return getLottery(balldata);
            }
        },
        //二同号单选
        dx160: function(ballData){
            var total = getPickBallCount(ballData["ball"][0]) * getPickBallCount(ballData["ball"][1]);
            var sameCount = 0;
            for (var i = 0; i < 6; i++) {
                if (ballData["ball"][0][i] == 1 && ballData["ball"][1][i] == 1) {
                    sameCount++;
                }
            }
            return total - sameCount;
        },
    };

    var planNum = function(balldata) {
        var singleNum = 0;
        switch(androidJs.getMethodName()){
             case 'danshuang'://单双
             case 'daxiao810'://大小810
             case 'wuxing'://五行
                    singleNum = getLottery(balldata);
                    break;
            case 'qianerfushi':
            case 'houerfushi':
                switch(androidJs.getMethodId()){
                    case 20:
                    case 59:
                        singleNum = budingweiGetLottery(balldata,2); //后二组选
                        break;
                    case 134:
                    case 135:
                        singleNum = getFirstLineCombine(balldata, 2);
                        break;
                    default:
                        singleNum=getLottery(balldata);
                        break;
                }
                /*if(androidJs.getMethodId()==20||androidJs.getMethodId()==59){
                    singleNum=budingweiGetLottery(balldata,2);
                }else{
                    singleNum=getLottery(balldata);
                }*/
                break;
            case "fushi":   //复式
                switch(androidJs.getMethodId()) {
                    case 78://定位
                        singleNum=getLocate(balldata);
                        break;
                    case 108:
                        singleNum = getFirstLineCombine(balldata, 3);
                        break;
                    case 107:
                        singleNum = getFirstLineCombine(balldata, 2);
                        break;
                    case 111:
                    case 112:
                        singleNum=getLotteryDistinct(balldata);
                        break;
                    default:
                        singleNum=getLottery(balldata);
                }
                /*if(androidJs.getMethodId()==78){//定位
                    singleNum=getLocate(balldata);
                }else{
                    singleNum=getLottery(balldata);
                }*/
                break;
            case "qianerhezhi":
            case "houerhezhi":
                if(androidJs.getMethodId()==76||androidJs.getMethodId()==77){
                    singleNum=erxingzuxuanhezhiGetLottery(balldata);
                }else{
                    singleNum=erxinghezhiGetLottery(balldata);
                }
                break;
            case "hezhi":   //组选和值
                switch(androidJs.getMethodId()){
                     case 175:
                        singleNum=getLottery(balldata);
                        break;
                     case 75:
                     case 80:
                     case 154:
                     case 140:
                        singleNum=zuxuanhezhiGetLottery(balldata);
                        break;
                     default: //直选和值
                        singleNum=hezhiGetLottery(balldata);
                }
                /*if(==175){
                    singleNum=getLottery(balldata);
                }else if(androidJs.getMethodId()==154||androidJs.getMethodId()==75||androidJs.getMethodId()==80){
                    singleNum=zuxuanhezhiGetLottery(balldata);
                }else{      //直选和值
                    singleNum=hezhiGetLottery(balldata);
                }*/
                break;
            case "teshuhaoma":
            case "baozi":
            case "shunzi":
            case "duizi":
                singleNum=getLocate(balldata);
                break;
            case "baodan": //包胆
                if(getPickBallCount(balldata["ball"][0]) > 0){
                    singleNum=54;
                }else{
                    singleNum=0;
                }
                break;
            case "houerkuadu":
            case "qianerkuadu":
                singleNum=erxingkuaduGetLottery(balldata);
                break;
            case "kuadu":  //跨度
                singleNum=kuaduGetLottery(balldata);
                break;
            case "zuliu":  //组六
                singleNum = getFirstLineCombine(balldata, 3);
                break;
            case "zusan":　//组三
                singleNum = 2 * getFirstLineCombine(balldata, 2);
                break;
            case "zuxuan120":   //组选120
                singleNum=ZX120getLottery(balldata);
                break;
            case "zuxuan60":    //组选60
                singleNum=ZX60getLottery(balldata);
                break;
            case "zuxuan30":    //组选30
                singleNum=ZX30getLottery(balldata);
                break;
            case "zuxuan20":    //组选20
                singleNum=ZX20getLottery(balldata);
                break;
            case "zuxuan10":    //组选10
                singleNum=ZX10getLottery(balldata);
                break;
            case "zuxuan5":     //组选5
                singleNum=ZX5getLottery(balldata);
                break;
            case "zuxuan24":    //组选24
                singleNum=ZX24getLottery(balldata);
                break;
            case "zuxuan12":    //组选12
                singleNum=ZX12getLottery(balldata);
                break;
            case "zuxuan6":    //组选6
                singleNum=ZX6getLottery(balldata);
                break;
            case "zuxuan4":    //组选4
                singleNum=ZX4getLottery(balldata);
                break;
            case "housanyimabudingwei": //不定位
                singleNum=getFirstLineCombine(balldata,1);
                break;
            case "erma":
            case "housanermabudingwei":
                singleNum=getFirstLineCombine(balldata,2);
                break;
            case "qiansanyimabudingwei":
                singleNum=getFirstLineCombine(balldata,1);
                break;
            case "qiansanermabudingwei":
                singleNum=getFirstLineCombine(balldata,2);
                break;
            case "sixingyimabudingwei":
                singleNum=getFirstLineCombine(balldata,1);
                break;
            case "sixingermabudingwei":
                singleNum=getFirstLineCombine(balldata,2);
                break;
            case "hezhiweishu":
                singleNum=getFirstLineCombine(balldata,1);
                break;
            case "wuxingermabudingwei":
                singleNum=getFirstLineCombine(balldata,2);
                break;
            case "wuxingsanmabudingwei":
                singleNum=getFirstLineCombine(balldata,3);
                break;
            case "sanxingdingweidan":
                singleNum=getLocate(balldata);
                break;
            case "houerdaxiaodanshuang": //后二大小单双
            case "housandaxiaodanshuang":
            case "qianerdaxiaodanshuang":
            case "qiansandaxiaodanshuang":
            case "wuxinghezhidaxiaodanshuang":
            case "yifanfengshun":
            case "haoshichengshuang":
            case "sanxingbaoxi":
            case "sijifacai":
                singleNum=getLottery(balldata);
                break;
            case "wq":
            case "wb":
            case "ws":
            case "wg":
            case "qb":
            case "qs":
            case "qg":
            case "bs":
            case "bg":
            case "sg":
                singleNum=0;
                var data=balldata["ball"][0]
                for(var i=0,len=data.length;i<len;i++){
                    if(data[i]!=-1){
                        singleNum+=1;
                    }
                }
                break;
            case "wumaquweisanxing":
            case "simaquweisanxing":
            case "housanquweierxing":
            case "qiansanquweierxing":
            case "wumaqujiansanxing":
            case "simaqujiansanxing":
            case "housanqujianerxing":
            case "qiansanqujianerxing":
                singleNum=getLottery(balldata);
                break;

            //十一选五
            case "budingwei":
            case "renxuanyi":
                singleNum=getLottery(balldata);
                break;
            case "dingdanshuang":
            case "caizhongwei":
            case "dingweidan":
                singleNum=getLocate(balldata);
                break;
            case "cbc1":
                singleNum = getFirstLineCombine(balldata, 1);
                break;
            case "cbc2":
                singleNum = getFirstLineCombine(balldata, 2);
                break;
            case "cbc3":
                singleNum = getFirstLineCombine(balldata, 3);
                break;
            case "cbc4":
                singleNum = getFirstLineCombine(balldata, 4);
                break;
            case "cbc5":
                singleNum = getFirstLineCombine(balldata, 5);
                break;
            case "renxuaner":
                if(androidJs.getMethodId()==113)
                    singleNum=getDanTuo(balldata,2);
                else
                    singleNum = getFirstLineCombine(balldata, 2);
                break;
            case "renxuansan":
                if(androidJs.getMethodId()==114)
                    singleNum=getDanTuo(balldata,3);
                else
                    singleNum = getFirstLineCombine(balldata, 3);
                break;
            case "renxuansi":
                if(androidJs.getMethodId()==115)
                    singleNum=getDanTuo(balldata,4);
                else
                    singleNum = getFirstLineCombine(balldata, 4);
                break;
            case "renxuanwu":
                if(androidJs.getMethodId()==116)
                    singleNum=getDanTuo(balldata,5);
                else
                    singleNum = getFirstLineCombine(balldata, 5);
                break;
            case "renxuanliu":
                if(androidJs.getMethodId()==117)
                    singleNum=getDanTuo(balldata,6);
                else
                    singleNum = getFirstLineCombine(balldata, 6);
                break;
            case "renxuanqi":
                if(androidJs.getMethodId()==118)
                    singleNum=getDanTuo(balldata,7);
                else
                    singleNum = getFirstLineCombine(balldata, 7);
                break;
            case "renxuanba":
                if(androidJs.getMethodId()==119)
                    singleNum=getDanTuo(balldata,8);
                else
                    singleNum = getFirstLineCombine(balldata, 8);
                break;
            case "dantuo":
                switch(androidJs.getMethodId()) {
                    case 121:
                        singleNum=getDanTuo(balldata,3);
                        break;
                    case 120:
                        singleNum=getDanTuo(balldata,2);
                        break;
                }
                break;

            //PK10,KL10 by ace
            case "dxds":
                singleNum=getLottery(balldata);
                break;
            case "longhu":
            case "cmc":
                singleNum=getLocate(balldata);
                break;
            case "fs": //快乐10 by ace
                switch(androidJs.getMethodId()){
                    case 416:
                        singleNum=C(getPickBallCount(balldata["ball"][0]),3);
                        break;
                    case 417:
                        singleNum=arithmetic(balldata);
                        break;
                    case 426:
                    case 400:
                        singleNum=getLocate(balldata);
                        break;
                    case 453:
                        singleNum=budingweiGetLottery(balldata,2); //KL10 二星组选
                        break;
                    case 452:
                        singleNum=budingweiGetLottery(balldata,3); //KL10 三星组选
                        break;
                    case 429://1.彩系： 北京快乐8（彩系：KENO） 台湾宾果（彩系：KENO） 苹果快乐8分分彩（彩系：KENO） 2.玩法：奇偶和
                    case 430://1.彩系： 北京快乐8（彩系：KENO） 台湾宾果（彩系：KENO） 苹果快乐8分分彩（彩系：KENO） 2.玩法：上中下
                        singleNum = getLottery(balldata);
                         break;
                    case 401:
                        singleNum = getKuaiLe8(balldata,2);
                        break;
                    case 402:
                        singleNum = getKuaiLe8(balldata,3);
                        break;
                    case 403:
                        singleNum = getKuaiLe8(balldata,4);
                        break;
                    case 404:
                        singleNum = getKuaiLe8(balldata,5);
                        break;
                    case 405:
                        singleNum = getKuaiLe8(balldata,6);
                        break;
                    case 406:
                        singleNum = getKuaiLe8(balldata,7);
                        break;
                    default:
                        singleNum=getLotteryDistinct(balldata);
                }
                break;

            //快乐十二
            case "rx1":
                singleNum=getFirstLineCombine(balldata,1);
                break;
            case "rx2":
                switch(androidJs.getMethodId())
                {
                    case 410:
                        singleNum=getFirstLineCombine(balldata,2);
                        break;
                    case 477: //任选胆拖 二
                        singleNum=getDanTuo(balldata,2);
                        break;
                    case 448: //任选复式 二
                        singleNum=getFirstLineCombine(balldata, 2);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,2);
                }
                break;
            case "rx3":
                switch(androidJs.getMethodId())
                {
                    case 411:
                        singleNum=getFirstLineCombine(balldata,3);
                        break;
                    case 478: //三
                        singleNum=getDanTuo(balldata,3);
                        break;
                    case 449: //三
                        singleNum=getFirstLineCombine(balldata, 3);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,3);
                }
                break;
            case "rx4":
                switch(androidJs.getMethodId())
                {
                    case 412:
                        singleNum=getFirstLineCombine(balldata,4);
                        break;
                    case 479: //四
                        singleNum=getDanTuo(balldata,4);
                        break;
                    case 450: //四
                        singleNum=getFirstLineCombine(balldata, 4);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,4);
                }
                break;
            case "rx5":
                switch(androidJs.getMethodId())
                {
                    case 413:
                        singleNum=getFirstLineCombine(balldata,5);
                        break;
                    case 480: //五
                        singleNum=getDanTuo(balldata,5);
                        break;
                    case 451: //五
                        singleNum=getFirstLineCombine(balldata, 5);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,5);
                }
                break;
            case "rx6":
                switch(androidJs.getMethodId())
                {
                    case 461:
                        singleNum=getFirstLineCombine(balldata,6);
                        break;
                    case 483: //六
                        singleNum=getDanTuo(balldata,6);
                        break;
                    case 457: //六
                        singleNum=getFirstLineCombine(balldata, 6);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,6);
                }
                 break;
            case "rx7":
                switch(androidJs.getMethodId())
                {
                    case 462:
                        singleNum=getFirstLineCombine(balldata,7);
                        break;
                    case 458: //七
                        singleNum=getFirstLineCombine(balldata, 7);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,7);
                }
                break;
            case "rx8":
                switch(androidJs.getMethodId())
                {
                    case 463:
                        singleNum=getFirstLineCombine(balldata,8);
                        break;
                    case 484: //七
                        singleNum=getDanTuo(balldata,7);
                        break;
                    default:
                        singleNum=getDanTuo(balldata,8);
                }
                break;
            case "dt":
                switch(androidJs.getMethodId())
                {
                    case 471:
                        singleNum=getDanTuo(balldata,3);
                        break;
                    case 472:
                        singleNum=getDanTuo(balldata,2);
                    case 482: // KL10 前二组选胆拖
                        singleNum=getDanTuo(balldata,2);
                        break;
                    case 481: // KL10 前三组选胆拖
                        singleNum=getDanTuo(balldata,3);
                }
                break;
            case "dt":
                switch(androidJs.getMethodId()){
                    case 482: // KL10 前二组选胆拖
                        singleNum=getDanTuo(balldata,2);
                        break;
                    case 481: // KL10 前三组选胆拖
                        singleNum=getDanTuo(balldata,3);
                        break;
                }
                break;
            default:
                singleNum=0;
                break;
        }
        return singleNum;
    };

    //等差数列
    var arithmetic=function(ballData)
    {
        var sum=0;
        for(i=0;i<getPickBallCount(ballData.ball[0]);i++)
        {
            sum+=i;
        }
        return sum;
    }

    var C=function(m,n){ return m>=n ? jc(m)/(jc(n)*jc(m-n)) : 0; };
    var jc=function(n){ var re=1; for(i=n;i>1;i--){re*=i;}return re; };
    //var getCount=function(ball){ return ball.sort().indexOf(1)!=-1? ball.length - ball.sort().indexOf(1) : 0; };

    var getDanTuo = function (ballData, num) {
        var dmsLength = getPickBallCount(ballData.ball[0]),tmsLength = getPickBallCount(ballData.ball[1]);
        //dmsLength = dmsLength > ballData.ball[0].length - 1 ? 0 : dmsLength;
        //tmsLength = tmsLength > ballData.ball[1].length - 1 ? 0 : tmsLength;

        return (dmsLength > 0 && tmsLength > 0 )? C(tmsLength, num - dmsLength) : 0;
    };

    var getKuaiLe8 = function (ballData,num){
        return math.combine(getPickBallCount(ballData.ball[0])+getPickBallCount(ballData.ball[1]),num);
        //return C(getPickBallCount(ballData.ball[0])+getPickBallCount(ballData.ball[1]),num);
    };

	// 获取总注数（乐透彩）
    var getLotteryDistinct = function(ballData) {
        // 球数据
    	/*ballData ={
    		"ball" : [[0,0,0,0,1,1,0,0,0,0],
    			  	  [0,0,0,0,1,1,0,0,0,0],
    			      [0,0,0,0,1,0,1,0,0,0]]
    	};*/
        var me = this,
            data = ballData["ball"],
            i = 0,
            len = data.length,
            row, isEmptySelect = true,
            j = 0,
            len2 = 0,
            result = [], total = 1, rowNum = 0;
        //检测球是否完整
        for (; i < len; i++) {
            result[i] = [];
            row = data[i];
            len2 = row.length;
            isEmptySelect = true;
            rowNum = 0;
            for (j = 0; j < len2; j++) {
                if (row[j] > 0) {
                    isEmptySelect = false;
					result[i].push(j);
                    rowNum++;
                }
            }
        };
        result = deleteDupData(combinationDistinct(result));
        return result.length;
    };

    // 组合结果（乐透彩）
    var combinationDistinct = function(arr2) {
		if (arr2.length < 1) {
			return [];
		}
		var w = arr2[0].length,
			h = arr2.length,
			i, j,
			m = [],
			n,
			result = [],
			_row = [];

		m[i = h] = 1;

		while (i--) {
			m[i] = m[i + 1] * arr2[i].length;
		}
		n = m[0];
		for (i = 0; i < n; i++) {
			_row = [];
			for (j = 0; j < h; j++) {
				_row[j] = arr2[j][~~(i % m[j] / m[j + 1])];
			}
			result[i] = _row;
		}
		return result;
	};

	//过滤结果去重（乐透彩）
	var	deleteDupData = function(arr) {
	    var saveArray = [];
            for(var i=0,len=arr.length;i<len;i++){
                if(isGood(arr[i])){
                    saveArray.push(arr[i]);
                }
            }
            return saveArray;
	};

    var isGood =  function(arr){
        for(var i=0,len=arr.length;i<len;i++){
            for(var j=0,len2=arr.length;j<len2;j++){
                if(i!=j && arr[i]==arr[j]){
                    return false;
                }
            }
        }
        return true;
    }

    // 组合
    var combine= function(list, num, last) {
        var result = [],
            i = 0;
        last = last || [];
        if (num == 0) {
            return [last];
        }
        for (; i <= list.length - num; i++) {
            result = result.concat(arguments.callee(list.slice(i + 1), num - 1, last.slice(0).concat(list[i])));
        }
        return result;
    };
    //检查数组存在某数
    var arrIndexOf = function(value, arr) {
        var r = 0;
        for (var s = 0; s < arr.length; s++) {
            if (arr[s] == value) {
                r += 1;
            }
        }
        return r || -1;
    };
    //检测结果重复
    var checkResult= function(data, array){
        //检查重复
        for (var i = array.length - 1; i >= 0; i--) {
            if(array[i].join("") == data){
                return false;
            }
        };
        return true;
    };


    // 获取总注数
    var getLottery = function(ballData) {
        var me = this,
            data = ballData["ball"],
            i = 0,
            len = data.length,
            row, isEmptySelect = true,
            j = 0,
            len2 = 0,
            result = [], total = 1, rowNum = 0;
        //检测球是否完整
        for (; i < len; i++) {
            result[i] = [];
            row = data[i];
            len2 = row.length;
            isEmptySelect = true;
            rowNum = 0;
            for (j = 0; j < len2; j++) {
                if (row[j] > 0) {
                    isEmptySelect = false;
                    rowNum++;
                }
            }
            //计算注数
            total *= rowNum;
        };
        return total;
    };

    // 获取总注数
    var getLocate = function(ballData) {
        var me = this,
            data = ballData["ball"],
            i = 0,
            len = data.length,
            row, isEmptySelect = true,
            j = 0,
            len2 = 0,
            result = [], total = 0, rowNum = 0;
        //检测球是否完整
        for (; i < len; i++) {
            result[i] = [];
            row = data[i];
            len2 = row.length;
            isEmptySelect = true;
            rowNum = 0;
            for (j = 0; j < len2; j++) {
                if (row[j] > 0) {
                    isEmptySelect = false;
                    rowNum++;
                }
            }
            //计算注数
            total += rowNum;
        };
        return total;
    };

    // 二星 和值总方法
    var erxinghezhiGetLottery= function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,arr = [],resultNum = [];
        //计算和值的各种结果
        /* sum = 和值 的值 nBegin = 开始值 nEnd = 结束值  */
        var mathResult = function(sum, nBegin, nEnd){
            var arr = [], x,y;
            for (x=nBegin;x<=nEnd ;x++ ){
                for (y=nBegin;y<=nEnd ;y++ ){
                    if(x+y==sum){
                        arr.push([x,y]);
                    }
                }
            }
            return arr;
        };
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        //校验当前的面板
        //获取选中数字
        for(var j=0;j < arr.length;j++){
            resultNum = resultNum.concat(mathResult(arr[j], 0, 9));
        }
        return resultNum.length;
    };

    // 三星 和值总方法
    var hezhiGetLottery= function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,arr = [],resultNum = [];
        //计算和值的各种结果
        /* sum = 和值 的值 nBegin = 开始值 nEnd = 结束值  */
        var mathResult = function(sum, nBegin, nEnd){
            var arr = [], x,y,z;

            for (x=nBegin;x<=nEnd ;x++ ){
                for (y=nBegin;y<=nEnd ;y++ ){
                    for (z=nBegin;z<=nEnd ;z++ ){
                        if(x+y+z==sum){
                            arr.push([x,y,z]);
                        }
                    }
                }
            }
            return arr;
        };

        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        //校验当前的面板
        //获取选中数字
        for(var j=0;j < arr.length;j++){
            resultNum = resultNum.concat(mathResult(arr[j], 0, 9));
        }
        return resultNum.length;
    };

    var erxingkuaduGetLottery= function(ballData){
        var data = ballData["ball"][0],i = 0,len = data.length,j = 0,len2,isEmptySelect = true,numArr = [],result = [];
        var mathResult = function(num){
            var i = 0, len, j = 0,len2, result = [];
            for(;i < 10;i++){
                for(j= 0;j < 10;j++){
                    var numList = [i,j];
                    minNums = Math.min.apply(Math, numList);
                    maxNums = Math.max.apply(Math, numList);
                    if(maxNums - minNums == num){
                        result.push(numList);
                    }
                }
            }
            return result;
        }
        for(i = 0;i < len;i++){
            if(data[i] > 0){
                isEmptySelect = false;
                numArr.push(i);
            }
        }
        if(isEmptySelect){
            isBallsComplete = false;
            return [];
        }
        isBallsComplete = true;

        for(i = 0,len = numArr.length;i < len;i++){
            result = result.concat(mathResult(numArr[i]));
        }
        return result.length;
    };

    // 跨度方法
    var kuaduGetLottery= function(ballData){
        var data = ballData["ball"][0],i = 0,len = data.length,j = 0,len2,isEmptySelect = true,numArr = [],result = [];
        var mathResult = function(num){
            var i = 0, len, j = 0, k = 0, len2, result = [];
            for(;i < 10;i++){
                for(j= 0;j < 10;j++){
                    for(k= 0;k < 10;k++){
                        var numList = [i,j,k];
                        minNums = Math.min.apply(Math, numList);
                        maxNums = Math.max.apply(Math, numList);
                        if(maxNums - minNums == num){
                            result.push(numList);
                        }
                    }
                }
            }
            return result;
        }
        for(i = 0;i < len;i++){
            if(data[i] > 0){
                isEmptySelect = false;
                numArr.push(i);
            }
        }
        if(isEmptySelect){
            isBallsComplete = false;
            return [];
        }
        isBallsComplete = true;

        for(i = 0,len = numArr.length;i < len;i++){
            result = result.concat(mathResult(numArr[i]));
        }
        return result.length;
    };

    // 二星　组选和值
    var erxingzuxuanhezhiGetLottery= function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,arr = [],resultNum = [];
        var mathResult = function(sum, nBegin, nEnd){
            var arr = [],checkArray = [],x,y;
            for (x=nBegin;x<=nEnd ;x++ ){
                for (y=nBegin+1;y<=nEnd ;y++ ){
                    if(x+y==sum && arrIndexOf(x, [x,y]) != 2){
                        var postArray = [x,y].sort(function(a, b){
                            return a-b;
                        });
                        if(checkResult(postArray.join(""), checkArray)){
                            checkArray.push(postArray)
                            arr.push([x,y]);
                        }
                    }
                }
            }
            return arr;
        };
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        //校验当前的面板　获取选中数字
        for(var j=0;j < arr.length;j++){
            console.log("arr->"+arr[j]);
            resultNum = resultNum.concat(mathResult(arr[j]+1, 0, 9));
        }

        return resultNum.length;
    };

    //三星 组选和值
    var zuxuanhezhiGetLottery= function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,arr = [],resultNum = [];
        var mathResult = function(sum, nBegin, nEnd){
            var arr = [],checkArray = [],x,y,z;
            for (x=nBegin;x<=nEnd ;x++ ){
                for (y=nBegin;y<=nEnd ;y++ ){
                    for (z=nBegin+1;z<=nEnd ;z++ ){
                        if(x+y+z==sum && arrIndexOf(x, [x,y,z]) != 3){
                            var postArray = [x,y,z].sort(function(a, b){
                                return a-b;
                            });
                            if(checkResult(postArray.join(""), checkArray)){
                                checkArray.push(postArray)
                                arr.push([x,y,z]);
                            }
                        }
                    }
                }
            }
            return arr;
        };

        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        //校验当前的面板　获取选中数字
        for(var j=0;j < arr.length;j++){
            resultNum = resultNum.concat(mathResult(arr[j]+1, 0, 9));
        }
        return resultNum.length;
    };

    // 组选120
    var ZX120getLottery=function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,result,arr = [];
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        result = combine(arr, 5);
        return result.length;
    };

    // 组选60
    var ZX60getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result = [],arr = [], nr = new Array();
        //校验当前的面板 获取选中数字
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 3);
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    // 组选30
    var ZX30getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result,arr = [], nr = new Array();
        for(;i < len;i++){
            if(ball[0][i] > 0){
                arr.push(i);
            }
        }
        for(var c=0;c<arr.length;c++){
            arr[c] = [arr[c], arr[c]];
        }
        //存储单号组合
        result = combine(arr, 2);
        //二重号组合
        for(var i=0,current;i<ball[1].length;i++){
            if(ball[1][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i]));
                    }
                }
            }
        }
        return nr.length;
    };

    // 组选20
    var ZX20getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result,arr = [], nr = new Array();
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 2);
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    // 组选10
    var ZX10getLottery=function(ballData){
        var ball = ballData["ball"], i = 0, len = ball[1].length, result ,arr = [], nr = new Array();
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 1);
        for(var c=0;c<result.length;c++){
            result[c] = [result[c][0], result[c][0]];
        }
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    // 组选5
    var ZX5getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result,arr = [], nr = new Array();
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 1);
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i,i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    var ZX24getLottery=function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,result,arr = [];
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        result = combine(arr, 4);
        return result.length;
    };

    // 组选12
    var ZX12getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result,arr = [],nr = new Array();
        //校验当前的面板 获取选中数字
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 2);
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    // 组选6
    var ZX6getLottery=function(ballData){
        var ball = ballData["ball"][0],i = 0,len = ball.length,result = [],arr = [], nr = new Array();
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        for(var c=0;c<arr.length;c++){
            arr[c] = [arr[c], arr[c]];
        }
        result = combine(arr, 2);
        return result.length;
    };

    // 组选4
    var ZX4getLottery=function(ballData){
        var ball = ballData["ball"],i = 0,len = ball[1].length,result , arr = [], nr = new Array() ;
        for(;i < len;i++){
            if(ball[1][i] > 0){
                arr.push(i);
            }
        }
        //存储单号组合
        result = combine(arr, 1);
        //二重号组合
        for(var i=0,current;i<ball[0].length;i++){
            if(ball[0][i] == 1){
                //加上单号各种组合
                for(var s=0;s<result.length;s++){
                    if(arrIndexOf(i, result[s]) == -1){
                        nr.push(result[s].concat([i,i,i]));
                    }
                }
            }
        }
        return nr.length;
    };

    //不定位与组选 by ace
    var budingweiGetLottery= function(ballData,num){
        var ball = ballData["ball"][0], i = 0, len = ball.length, arr = [], result;
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        result = combine(arr, num)
        //校验当前的面板 获取选中数字
        return result.length;
    };

    //包胆 by ace
    var baodanGetLottery= function(ballData){
        var ball = ballData["ball"][0], i = 0, len = ball.length, arr = [];
        for(;i < len;i++){
            if(ball[i] > 0){
                arr.push(i);
            }
        }
        return arr.length;
    };

    //获取第一列选中球的组合数。by alashi
    var getFirstLineCombine = function(ballData, n) {
		return math.combine(getPickBallCount(ballData["ball"][0]), n);
	};
    
    //获取list里的选中的球的个数。ballList=[1,-1,1,-1]。by alashi
    var getPickBallCount = function(ballList) {
        var count = 0;
        for (var value in ballList) {
            if (ballList[value] > 0) {
                count++;
            }
        }
        return count;
    };
})(jQuery);

//通用数学计算方法. by alashi
//大数字专用 by Sakura
var math = {
    //阶乘的结果缓存
    factorialCache:[],

    //计算组合：从m个不同的元素中，任取n（n≤m）个元素为一组，叫作从m个不同元素中取出n个元素的进行组合
    combine: function (m, n) {
        return m >= n ? Math.round(math.factorial(m) / (math.factorial(n) * math.factorial(m - n))) : 0;
    },

    //计算n的阶乘
    factorial: function (n) {
        if (math.factorialCache[n]) {
            //console.log("use cache : " + n + "! = " +math.factorialCache[n])
            return math.factorialCache[n];
        }
        var re = 1;
        for(var i = n; i > 1; i--){
            re *= i;
        }
        math.factorialCache[n] = re;
        return re;
    },
}

function calculate() {
     var data = androidJs.getData();
     console.log(data);
     if (typeof data == "undefined") {
         console.log("androidJs.getData() == null");
         return;
     }
     data = JSON.parse(data);
     var result;
     $(function () { result = $.BallData(data); });
     console.log("final:"+result.singleNum);
     androidJs.result(result.singleNum);
 }