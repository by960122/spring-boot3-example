package com.example.tool;

import com.example.exception.CustomException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author: BYDylan
 * @date: 2023/8/2
 * @description:
 */
class IpToolsTest {

    @Test
    void ipv4FormatTest() {
        Assert.assertThrows("ipv4格式用例失败", CustomException.class, () -> IpTools.checkIpv4Format("...."));
    }

    @Test
    void ipv4MaskFormatTest() {
        Assert.assertThrows("ipv4掩码格式用例失败", CustomException.class, () -> IpTools.checkIpv4MaskFormat(666));
    }

    @Test
    void ipv6FormatTest() {
        Assert.assertThrows("ipv6格式用例失败", CustomException.class, () -> IpTools.checkIpv6Format("666"));
    }

    @Test
    void ipv6MaskFormatTest() {
        Assert.assertThrows("ipv6掩码格式用例失败", CustomException.class, () -> IpTools.checkIpv6MaskFormat(666));
    }

    @Test
    void getIpv6SegmentRange() {
        IpTools.IpRange ipv6SegmentRange = IpTools.getIpv6SegmentRange("2c0f:f7c0:3000:d:0:0:0:0/120");
        Assert.assertEquals("ipv6段开始ip用例失败", "2c0f:f7c0:3000:d:0:0:0:0", ipv6SegmentRange.getStart());
        Assert.assertEquals("ipv6段结束ip用例失败", "2c0f:f7c0:3000:d:0:0:0:ff", ipv6SegmentRange.getEnd());
    }
}