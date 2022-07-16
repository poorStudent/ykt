package com.vms.ykt.yktStuWeb.Cqooc;

public class SHA256
{
   static String js2="(function () {\n" +
            "  var CryptoJS = CryptoJS || function (h, s) {\n" +
            "    var f = {\n" +
            "    },\n" +
            "    t = f.lib = {\n" +
            "    },\n" +
            "    g = function () {\n" +
            "    },\n" +
            "    j = t.Base = {\n" +
            "      extend: function (a) {\n" +
            "        g.prototype = this;\n" +
            "        var c = new g;\n" +
            "        a && c.mixIn(a);\n" +
            "        c.hasOwnProperty('init') || (c.init = function () {\n" +
            "          c.$super.init.apply(this, arguments)\n" +
            "        });\n" +
            "        c.init.prototype = c;\n" +
            "        c.$super = this;\n" +
            "        return c\n" +
            "      },\n" +
            "      create: function () {\n" +
            "        var a = this.extend();\n" +
            "        a.init.apply(a, arguments);\n" +
            "        return a\n" +
            "      },\n" +
            "      init: function () {\n" +
            "      },\n" +
            "      mixIn: function (a) {\n" +
            "        for (var c in a) a.hasOwnProperty(c) && (this[c] = a[c]);\n" +
            "        a.hasOwnProperty('toString') && (this.toString = a.toString)\n" +
            "      },\n" +
            "      clone: function () {\n" +
            "        return this.init.prototype.extend(this)\n" +
            "      }\n" +
            "    },\n" +
            "    q = t.WordArray = j.extend({\n" +
            "      init: function (a, c) {\n" +
            "        a = this.words = a || [\n" +
            "        ];\n" +
            "        this.sigBytes = c != s ? c : 4 * a.length\n" +
            "      },\n" +
            "      toString: function (a) {\n" +
            "        return (a || u).stringify(this)\n" +
            "      },\n" +
            "      concat: function (a) {\n" +
            "        var c = this.words,\n" +
            "        d = a.words,\n" +
            "        b = this.sigBytes;\n" +
            "        a = a.sigBytes;\n" +
            "        this.clamp();\n" +
            "        if (b % 4) for (var e = 0; e < a; e++) c[b + e >>> 2] |= (d[e >>> 2] >>> 24 - 8 * (e % 4) & 255) << 24 - 8 * ((b + e) % 4);\n" +
            "         else if (65535 < d.length) for (e = 0; e < a; e += 4) c[b + e >>> 2] = d[e >>> 2];\n" +
            "         else c.push.apply(c, d);\n" +
            "        this.sigBytes += a;\n" +
            "        return this\n" +
            "      },\n" +
            "      clamp: function () {\n" +
            "        var a = this.words,\n" +
            "        c = this.sigBytes;\n" +
            "        a[c >>> 2] &= 4294967295 << 32 - 8 * (c % 4);\n" +
            "        a.length = h.ceil(c / 4)\n" +
            "      },\n" +
            "      clone: function () {\n" +
            "        var a = j.clone.call(this);\n" +
            "        a.words = this.words.slice(0);\n" +
            "        return a\n" +
            "      },\n" +
            "      random: function (a) {\n" +
            "        for (var c = [\n" +
            "        ], d = 0; d < a; d += 4) c.push(4294967296 * h.random() | 0);\n" +
            "        return new q.init(c, a)\n" +
            "      }\n" +
            "    }),\n" +
            "    v = f.enc = {\n" +
            "    },\n" +
            "    u = v.Hex = {\n" +
            "      stringify: function (a) {\n" +
            "        var c = a.words;\n" +
            "        a = a.sigBytes;\n" +
            "        for (var d = [\n" +
            "        ], b = 0; b < a; b++) {\n" +
            "          var e = c[b >>> 2] >>> 24 - 8 * (b % 4) & 255;\n" +
            "          d.push((e >>> 4).toString(16));\n" +
            "          d.push((e & 15).toString(16))\n" +
            "        }\n" +
            "        return d.join('')\n" +
            "      },\n" +
            "      parse: function (a) {\n" +
            "        for (var c = a.length, d = [\n" +
            "        ], b = 0; b < c; b += 2) d[b >>> 3] |= parseInt(a.substr(b, 2), 16) << 24 - 4 * (b % 8);\n" +
            "        return new q.init(d, c / 2)\n" +
            "      }\n" +
            "    },\n" +
            "    k = v.Latin1 = {\n" +
            "      stringify: function (a) {\n" +
            "        var c = a.words;\n" +
            "        a = a.sigBytes;\n" +
            "        for (var d = [\n" +
            "        ], b = 0; b < a; b++) d.push(String.fromCharCode(c[b >>> 2] >>> 24 - 8 * (b % 4) & 255));\n" +
            "        return d.join('')\n" +
            "      },\n" +
            "      parse: function (a) {\n" +
            "        for (var c = a.length, d = [\n" +
            "        ], b = 0; b < c; b++) d[b >>> 2] |= (a.charCodeAt(b) & 255) << 24 - 8 * (b % 4);\n" +
            "        return new q.init(d, c)\n" +
            "      }\n" +
            "    },\n" +
            "    l = v.Utf8 = {\n" +
            "      stringify: function (a) {\n" +
            "        try {\n" +
            "          return decodeURIComponent(escape(k.stringify(a)))\n" +
            "        } catch (c) {\n" +
            "          throw Error('Malformed UTF-8 data');\n" +
            "        }\n" +
            "      },\n" +
            "      parse: function (a) {\n" +
            "        return k.parse(unescape(encodeURIComponent(a)))\n" +
            "      }\n" +
            "    },\n" +
            "    x = t.BufferedBlockAlgorithm = j.extend({\n" +
            "      reset: function () {\n" +
            "        this._data = new q.init;\n" +
            "        this._nDataBytes = 0\n" +
            "      },\n" +
            "      _append: function (a) {\n" +
            "        'string' == typeof a && (a = l.parse(a));\n" +
            "        this._data.concat(a);\n" +
            "        this._nDataBytes += a.sigBytes\n" +
            "      },\n" +
            "      _process: function (a) {\n" +
            "        var c = this._data,\n" +
            "        d = c.words,\n" +
            "        b = c.sigBytes,\n" +
            "        e = this.blockSize,\n" +
            "        f = b / (4 * e),\n" +
            "        f = a ? h.ceil(f) : h.max((f | 0) - this._minBufferSize, 0);\n" +
            "        a = f * e;\n" +
            "        b = h.min(4 * a, b);\n" +
            "        if (a) {\n" +
            "          for (var m = 0; m < a; m += e) this._doProcessBlock(d, m);\n" +
            "          m = d.splice(0, a);\n" +
            "          c.sigBytes -= b\n" +
            "        }\n" +
            "        return new q.init(m, b)\n" +
            "      },\n" +
            "      clone: function () {\n" +
            "        var a = j.clone.call(this);\n" +
            "        a._data = this._data.clone();\n" +
            "        return a\n" +
            "      },\n" +
            "      _minBufferSize: 0\n" +
            "    });\n" +
            "    t.Hasher = x.extend({\n" +
            "      cfg: j.extend(),\n" +
            "      init: function (a) {\n" +
            "        this.cfg = this.cfg.extend(a);\n" +
            "        this.reset()\n" +
            "      },\n" +
            "      reset: function () {\n" +
            "        x.reset.call(this);\n" +
            "        this._doReset()\n" +
            "      },\n" +
            "      update: function (a) {\n" +
            "        this._append(a);\n" +
            "        this._process();\n" +
            "        return this\n" +
            "      },\n" +
            "      finalize: function (a) {\n" +
            "        a && this._append(a);\n" +
            "        return this._doFinalize()\n" +
            "      },\n" +
            "      blockSize: 16,\n" +
            "      _createHelper: function (a) {\n" +
            "        return function (c, d) {\n" +
            "          return (new a.init(d)).finalize(c)\n" +
            "        }\n" +
            "      },\n" +
            "      _createHmacHelper: function (a) {\n" +
            "        return function (c, d) {\n" +
            "          return (new w.HMAC.init(a, d)).finalize(c)\n" +
            "        }\n" +
            "      }\n" +
            "    });\n" +
            "    var w = f.algo = {\n" +
            "    };\n" +
            "    return f\n" +
            "  }(Math);\n" +
            "  (function (h) {\n" +
            "    for (var s = CryptoJS, f = s.lib, t = f.WordArray, g = f.Hasher, f = s.algo, j = [\n" +
            "    ], q = [\n" +
            "    ], v = function (a) {\n" +
            "      return 4294967296 * (a - (a | 0)) | 0\n" +
            "    }, u = 2, k = 0; 64 > k; ) {\n" +
            "      var l;\n" +
            "      a: {\n" +
            "        l = u;\n" +
            "        for (var x = h.sqrt(l), w = 2; w <= x; w++) if (!(l % w)) {\n" +
            "          l = !1;\n" +
            "          break a\n" +
            "        }\n" +
            "        l = !0\n" +
            "      }\n" +
            "      l && (8 > k && (j[k] = v(h.pow(u, 0.5))), q[k] = v(h.pow(u, 1 / 3)), k++);\n" +
            "      u++\n" +
            "    }\n" +
            "    var a = [\n" +
            "    ],\n" +
            "    f = f.SHA256 = g.extend({\n" +
            "      _doReset: function () {\n" +
            "        this._hash = new t.init(j.slice(0))\n" +
            "      },\n" +
            "      _doProcessBlock: function (c, d) {\n" +
            "        for (var b = this._hash.words, e = b[0], f = b[1], m = b[2], h = b[3], p = b[4], j = b[5], k = b[6], l = b[7], n = 0; 64 > n; n++) {\n" +
            "          if (16 > n) a[n] = c[d + n] | 0;\n" +
            "           else {\n" +
            "            var r = a[n - 15],\n" +
            "            g = a[n - 2];\n" +
            "            a[n] = ((r << 25 | r >>> 7) ^ (r << 14 | r >>> 18) ^ r >>> 3) + a[n - 7] + ((g << 15 | g >>> 17) ^ (g << 13 | g >>> 19) ^ g >>> 10) + a[n - 16]\n" +
            "          }\n" +
            "          r = l + ((p << 26 | p >>> 6) ^ (p << 21 | p >>> 11) ^ (p << 7 | p >>> 25)) + (p & j ^ ~p & k) + q[n] + a[n];\n" +
            "          g = ((e << 30 | e >>> 2) ^ (e << 19 | e >>> 13) ^ (e << 10 | e >>> 22)) + (e & f ^ e & m ^ f & m);\n" +
            "          l = k;\n" +
            "          k = j;\n" +
            "          j = p;\n" +
            "          p = h + r | 0;\n" +
            "          h = m;\n" +
            "          m = f;\n" +
            "          f = e;\n" +
            "          e = r + g | 0\n" +
            "        }\n" +
            "        b[0] = b[0] + e | 0;\n" +
            "        b[1] = b[1] + f | 0;\n" +
            "        b[2] = b[2] + m | 0;\n" +
            "        b[3] = b[3] + h | 0;\n" +
            "        b[4] = b[4] + p | 0;\n" +
            "        b[5] = b[5] + j | 0;\n" +
            "        b[6] = b[6] + k | 0;\n" +
            "        b[7] = b[7] + l | 0\n" +
            "      },\n" +
            "      _doFinalize: function () {\n" +
            "        var a = this._data,\n" +
            "        d = a.words,\n" +
            "        b = 8 * this._nDataBytes,\n" +
            "        e = 8 * a.sigBytes;\n" +
            "        d[e >>> 5] |= 128 << 24 - e % 32;\n" +
            "        d[(e + 64 >>> 9 << 4) + 14] = h.floor(b / 4294967296);\n" +
            "        d[(e + 64 >>> 9 << 4) + 15] = b;\n" +
            "        a.sigBytes = 4 * d.length;\n" +
            "        this._process();\n" +
            "        return this._hash\n" +
            "      },\n" +
            "      clone: function () {\n" +
            "        var a = g.clone.call(this);\n" +
            "        a._hash = this._hash.clone();\n" +
            "        return a\n" +
            "      }\n" +
            "    });\n" +
            "    s.SHA256 = g._createHelper(f);\n" +
            "    s.HmacSHA256 = g._createHmacHelper(f)\n" +
            "  }) (Math);\n" +
            "  sha256 = function (msg) {\n" +
            "    return CryptoJS.SHA256(msg).toString().toUpperCase();\n" +
            "  };\n" +
            "\n" +
            "    cnonce=function(){\n" +
            "        var INT2HEX = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];\n" +
            "\n" +
            "        function toHEX(v) {\n" +
            "            var h = '';\n" +
            "            h += INT2HEX[v >>> 28 & 0xF];\n" +
            "            h += INT2HEX[v >>> 24 & 0xF];\n" +
            "            h += INT2HEX[v >>> 20 & 0xF];\n" +
            "            h += INT2HEX[v >>> 16 & 0xF];\n" +
            "            h += INT2HEX[v >>> 12 & 0xF];\n" +
            "            h += INT2HEX[v >>> 8 & 0xF];\n" +
            "            h += INT2HEX[v >>> 4 & 0xF];\n" +
            "            h += INT2HEX[v >>> 0 & 0xF];\n" +
            "            return h;\n" +
            "        }\n" +
            "\n" +
            "        return toHEX(Math.floor(Math.random() * Math.pow(2, 32))) + toHEX(Math.floor(Math.random() * Math.pow(2, 32)));\n" +
            "    }\n}) ();";
}
