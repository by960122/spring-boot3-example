package com.example.tool;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.example.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/8/2
 * @description:
 */
@Slf4j
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
    void getIpv6SegmentRangeTest() {
        IpTools.IpRange ipv6SegmentRange = IpTools.getIpv6SegmentRange("2c0f:f7c0:3000:d:0:0:0:0/120");
        Assert.assertEquals("ipv6段开始ip用例失败", "2c0f:f7c0:3000:d:0:0:0:0", ipv6SegmentRange.getStart());
        Assert.assertEquals("ipv6段结束ip用例失败", "2c0f:f7c0:3000:d:0:0:0:ff", ipv6SegmentRange.getEnd());
    }

    @Test
    void getFullIpv6SegmentRangeTest() {
        IpTools.IpRange ipv6SegmentRange = IpTools.getFullIpv6SegmentRange("2408:8756:d0fe:400::/65");
        log.info("getFullIpv6SegmentRangeTest : {}.", JsonTools.toJsonString(ipv6SegmentRange));
        Assert.assertEquals("ipv6段开始ip用例失败", "2408:8756:d0fe:400:0:0:0:0", ipv6SegmentRange.getStart());
        Assert.assertEquals("ipv6段结束ip用例失败", "2408:8756:d0fe:400:7fff:ffff:ffff:ffff", ipv6SegmentRange.getEnd());
    }

    @Test
    void getFullIpv6SegmentRangeSecondTest() {
        IpTools.IpRange ipv6SegmentRange = IpTools.getFullIpv6SegmentRangeSecond("2408:8756:d0fe:400::/65");
        log.info("getFullIpv6SegmentRangeSecondTest : {}.", JsonTools.toJsonString(ipv6SegmentRange));
        Assert.assertEquals("ipv6段开始ip用例失败", "2408:8756:d0fe:400:0:0:0:0", ipv6SegmentRange.getStart());
        Assert.assertEquals("ipv6段结束ip用例失败", "2408:8756:d0fe:400:ffff:ffff:ffff:ffff", ipv6SegmentRange.getEnd());
    }
}