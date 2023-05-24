  var t = this.getParams(x);
                var q = "";
                q = CommonUtil.encrypt(j.toJSON(t))

 getParams: function(p) {
            var q = {
                courseId: p.courseId,
                itemId: p.itemId,
                time1: CommonUtil.formatStr((new Date()).getTime(), 20),
                time2: CommonUtil.formatStr(parseInt(p.startTime), 20),
                time3: CommonUtil.formatStr(CommonUtil.timeToSeconds(p.videoTotalTime), 20),
                time4: CommonUtil.formatStr(parseInt(p.endTime), 20),
                videoIndex: p.videoIndex || i,
                time5: CommonUtil.formatStr(p.studyTimeLong, 20),
                terminalType: p.terminalType || o
            };
x：
'{"interval":true,"playComplete":false,"courseId":"39e272199dab487ba6f8f76115cbfd2c___","itemId":"376aac4ac14840e682b7e1da3b4130dd","position":34.1,"videoTotalTime":"00:01:12","startTime":1.1,"endTime":34.1,"studyTimeLong":33,"startTimeStr":"00:00:01","endTimeStr":"00:00:34"}'

CommonUtil.formatStr = function(c, a) {
    var l = "";
    var k = (c + "").length;
    if (k > 0) {
        if (k + 2 > a) {
            return c + ""
        } else {
            var g = a - k - 2;
            var h = 1;
            for (var e = 0; e < g; e++) {
                h = h * 10
            }
            var b = parseInt(Math.random() * h);
            var f = (b + "").length;
            if (f < g) {
                for (var d = f; d < g; d++) {
                    b = b * 10
                }
            }
            if (k >= 10) {
                l += k
            } else {
                l += "0" + k
            }
            l += c + (b + "")
        }
    } else {
        return c + ""
    }
    return l
}

CommonUtil.encrypt = function(e) {
    var b = "bGVhcm5zcGFjZWFlczEyMw=="; 
    var a = new CommonUtil.Base64();
    var c = a.decode(b);  //learnspaceaes123

    var f = CryptoJS.enc.Utf8.parse(c);
'{"words":[1818583410,1853059169,1667588453,1932603955],"sigBytes":16}'

    var d = CryptoJS.AES.encrypt(e, f, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return d.toString()
}