package com.example.tool;

import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.druid.util.StringUtils;
import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/8/2
 * @description:
 */
@Slf4j
public class IpTools {
    private static final long VALIDATE_DATA = Long.MAX_VALUE >> 31;
    private static final Map<Integer, String> NET_MASK = new HashMap<>();
    private static Pattern ipv4Pattern =
        Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    private static Pattern ipv6Pattern = Pattern.compile(
        "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$");

    static {
        NET_MASK.put(32, ipv4ConvertToString(VALIDATE_DATA));
        for (int index = 1; index < 32; index++) {
            NET_MASK.put(32 - index, ipv4ConvertToString(VALIDATE_DATA << index & VALIDATE_DATA));
        }
    }

    /**
     * 检查ipv4 ip格式
     *
     * @param ip ip
     */
    public static void checkIpv4Format(String ip) {
        if (StringUtils.isEmpty(ip)) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "the ip can not by empty.");
        }
        Matcher matcher = ipv4Pattern.matcher(ip);
        if (!matcher.matches()) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "the ipv4 format error: " + ip);
        }
    }

    /**
     * 检查ipv6 ip格式
     *
     * @param ip ip
     */
    public static void checkIpv6Format(String ip) {
        if (StringUtils.isEmpty(ip)) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "the ip can not by empty.");
        }
        Matcher matcher = ipv6Pattern.matcher(ip);
        if (!matcher.matches()) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "the ipv6 format error: " + ip);
        }
    }

    /**
     * 检查 ipv4 掩码格式
     *
     * @param mask 掩码
     */
    public static void checkIpv4MaskFormat(Integer mask) {
        if (Objects.isNull(mask) || mask > 32 || mask < 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "ipv4 mask format is incorrect: " + mask);
        }
    }

    /**
     * 检查 ipv6 掩码格式
     *
     * @param mask 掩码
     */
    public static void checkIpv6MaskFormat(Integer mask) {
        if (Objects.isNull(mask) || mask > 128 || mask < 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "ipv6 mask format is incorrect: " + mask);
        }
    }

    /**
     * 检查 传入的起始ip是否在 段定义范围内
     *
     * @param ipv6AndMask ipv6 段
     * @param ipBegin ipv6 开始ip
     * @param ipEnd ipv6 结束ip
     */
    public static void checkIpv6SegmentRange(String ipv6AndMask, String ipBegin, String ipEnd) {
        IpRange ipRange = getIpv6SegmentRange(ipv6AndMask);
        String theoryIpStart = ipRange.getStart();
        String theoryIpEnd = ipRange.getEnd();
        BigInteger theoryIpStartInt = ipConvertToBigInt(theoryIpStart);
        BigInteger theoryIpEndInt = ipConvertToBigInt(theoryIpEnd);

        BigInteger ipBeginInt = ipConvertToBigInt(ipBegin);
        BigInteger ipEndInt = ipConvertToBigInt(ipEnd);
        // 传入的 开始ip 应小于等于 结束ip
        if (ipBeginInt.compareTo(ipEndInt) > 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR,
                "the start ip should be smaller than the end ip.");
        }
        // 1.传入的 开始ip 应小于等于 理论结束ip
        if (ipBeginInt.compareTo(theoryIpEndInt) > 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR,
                "the start ip should be smaller than the theory end ip.");
        }
        // 2.传入的 开始ip 应大于等于 理论开始ip
        if (ipBeginInt.compareTo(theoryIpStartInt) < 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR,
                "the start ip should be greater than the theory start ip.");
        }
        // 3.传入的 结束ip 应小于等于 理论结束ip
        if (ipEndInt.compareTo(theoryIpEndInt) > 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR,
                "the end ip should be greater than the theory end ip.");
        }
        // 4.传入的 结束ip 应大于等于 理论开始ip
        if (ipEndInt.compareTo(theoryIpStartInt) < 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR,
                "the end ip should be greater than the theory start ip.");
        }
    }

    /**
     * 检查ipv6 ip 是否属于段内
     *
     * @param ipv6AndMask ipv6 段
     * @param ip ip
     */
    public static void checkIpv6SegmentIp(String ipv6AndMask, String ip) {
        IpRange ipRange = getIpv6SegmentRange(ipv6AndMask);
        String theoryIpStart = ipRange.getStart();
        String theoryIpEnd = ipRange.getEnd();
        BigInteger theoryIpStartInt = ipConvertToBigInt(theoryIpStart);
        BigInteger theoryIpEndInt = ipConvertToBigInt(theoryIpEnd);
        BigInteger ipInt = ipConvertToBigInt(ip);
        if (ipInt.compareTo(theoryIpStartInt) < 0 || ipInt.compareTo(theoryIpEndInt) > 0) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "ip is not within the segment range.");
        }
    }

    /**
     * 将 ip 转为 long 类型
     *
     * @param ipLong ip
     * @return long 类型
     */
    public static String ipv4ConvertToString(Long ipLong) {
        long[] mask = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
        long num;
        StringBuilder ip = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            num = (ipLong & mask[i]) >> (i * 8);
            if (i > 0)
                ip.insert(0, ".");
            ip.insert(0, Long.toString(num, 10));
        }
        return ip.toString();
    }

    /**
     * 将 ip 转为 long 类型
     *
     * @param ip ip
     * @return long 类型
     */
    public static Long ipv4ConvertToLong(String ip) {
        long ipLong = 0L;
        final String[] ipNums = ip.split("\\.");
        for (int index = 0; index < 4; ++index) {
            ipLong = ipLong << 8 | Integer.parseInt(ipNums[index]);
        }
        return ipLong;
    }

    /**
     * 获取 ipv4 网段的段ip
     *
     * @param ip ip
     * @param mask 掩码
     * @return 段ip
     */
    private static Long getIpv4SegmentLong(String ip, Integer mask) {
        return ipv4ConvertToLong(ip) & ipv4ConvertToLong(NET_MASK.get(mask));
    }

    /**
     * 获取 ipv4 网段的段ip 11.11.11.0/23 网段ip: 11.11.10.0
     *
     * @param ip ip
     * @param mask 掩码
     * @return 段ip
     */
    private static String getIpv4SegmentString(String ip, Integer mask) {
        return ipv4ConvertToString(getIpv4SegmentLong(ip, mask));
    }

    /**
     * 获取 ipv4 网段的广播ip
     *
     * @param ip ip
     * @param mask 掩码
     * @return 广播ip
     */
    private static Long getIpv4brocaseLong(String ip, Integer mask) {
        Long ipLong = ipv4ConvertToLong(ip);
        Long maskLong = ipv4ConvertToLong(NET_MASK.get(mask));
        long segmentLong = ipLong & maskLong;
        return segmentLong | (~maskLong & VALIDATE_DATA);
    }

    /**
     * 获取 ipv4 网段的广播ip 11.11.11.0/23 广播ip: 11.11.11.255
     *
     * @param ip ip
     * @param mask 掩码
     * @return 广播ip
     */
    private static String getIpv4brocaseString(String ip, Integer mask) {
        return ipv4ConvertToString(getIpv4brocaseLong(ip, mask));
    }

    /**
     * 将 ip 转为 超大数字
     *
     * @param ip ip
     * @return 超大数字
     */
    public static BigInteger ipConvertToBigInt(String ip) {
        ip = ip.replace(" ", "");
        try {
            if (!StringUtils.isEmpty(ip)) {
                byte[] bytes = InetAddress.getByName(ip).getAddress();
                return new BigInteger(1, bytes);
            }
        } catch (UnknownHostException e) {
            log.error("ip convert bigint. ip: {}, error: ", ip, e);
        }
        return new BigInteger("-1");
    }

    /**
     * 获取 ipv4 段的起始ip 输入 1.1.1.1/24 输出 {"start":"1.1.1.0","end":"1.1.1.255"}
     *
     * @param ipv4AndMask 段
     * @return 起始ip
     */
    public static IpRange getIpv4SegmentRange(String ipv4AndMask) {
        try {
            String[] parts = ipv4AndMask.split("/");
            String ipAddress = parts[0];
            int prefixLength = Integer.parseInt(parts[1]);
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            byte[] address = inetAddress.getAddress();
            int mask = 0xffffffff << (32 - prefixLength);
            byte[] netMask = {(byte)(mask >>> 24), (byte)(mask >>> 16), (byte)(mask >>> 8), (byte)(mask)};
            byte[] networkAddress = new byte[4];
            for (int index = 0; index < 4; index++) {
                networkAddress[index] = (byte)(address[index] & netMask[index]);
            }
            byte[] broadcastAddress = new byte[4];
            for (int index = 0; index < 4; index++) {
                broadcastAddress[index] = (byte)(address[index] | ~netMask[index]);
            }
            return new IpRange(InetAddress.getByAddress(networkAddress).getHostAddress(),
                InetAddress.getByAddress(broadcastAddress).getHostAddress());
        } catch (UnknownHostException e) {
            log.error("get ipv4 segment range error: ", e);
            return new IpRange("0.0.0.0", "0.0.0.0");
        }
    }

    /**
     * 获取ipv6段 起始ip 输入: 2c0f:f7c0:3000:d:0:0:0:0/120 输出:
     *
     * @param ipv6AndMask ipv6 段
     * @return ipv6 段 开始结束ip对象
     */
    public static IpRange getIpv6SegmentRange(String ipv6AndMask) {
        try {
            String[] ipv6Split = ipv6AndMask.split("/");
            InetAddress inetAddress = Inet6Address.getByName(ipv6Split[0]);
            int maskInt = Integer.parseInt(ipv6Split[1]);
            BigInteger start = new BigInteger(1, inetAddress.getAddress());
            BigInteger end = new BigInteger(1, inetAddress.getAddress());
            BigInteger mask = BigInteger.valueOf(-1).shiftLeft(128 - maskInt);
            start = start.and(mask);
            end = end.or(mask.not());
            return new IpRange(InetAddress.getByAddress(start.toByteArray()).getHostAddress(),
                InetAddress.getByAddress(end.toByteArray()).getHostAddress());
        } catch (Exception e) {
            log.error("get ipv6 segment range error: ", e);
            return new IpRange("0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0");
        }
    }

    /**
     * 特殊网段 fd00:0:0:0:0:0:0:0/16
     * 
     * @param ipv6AndMask ipv6 段
     * @return ipv6 段 开始结束ip对象
     */
    public static IpRange getFullIpv6SegmentRange(String ipv6AndMask) {
        try {
            String[] ipv6Split = ipv6AndMask.split("/");
            String ip = ipv6Split[0];
            int prefixLength = Integer.parseInt(ipv6Split[1]);
            Inet6Address inetAddress = (Inet6Address)InetAddress.getByName(ip);
            byte[] address = inetAddress.getAddress();
            byte[] mask = new byte[address.length];
            for (int index = 0; index < prefixLength; index++) {
                mask[index / 8] |= 1 << (7 - index % 8);
            }
            byte[] startAddress = new byte[address.length];
            byte[] endAddress = new byte[address.length];
            for (int index = 0; index < address.length; index++) {
                startAddress[index] = (byte)(address[index] & mask[index]);
                endAddress[index] = (byte)(address[index] | ~mask[index]);
            }
            return new IpRange(InetAddress.getByAddress(startAddress).getHostAddress(),
                InetAddress.getByAddress(endAddress).getHostAddress());
        } catch (Exception e) {
            log.error("get ipv6 segment range error: ", e);
            return new IpRange("0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0");
        }
    }

    /**
     * 同上, 不过这种IP段解析有问题 2408:8756:d0fe:400::/65
     * {"start":"2408:8756:d0fe:400:0:0:0:0","end":"2408:8756:d0fe:400:ffff:ffff:ffff:ffff"}
     * 
     * @param ipv6AndMask ipv6 段
     * @return ipv6 段 开始结束ip对象
     */
    public static IpRange getFullIpv6SegmentRangeSecond(String ipv6AndMask) {
        try {
            String[] ipv6Split = ipv6AndMask.split("/");
            String ip = ipv6Split[0];
            int prefixLength = Integer.parseInt(ipv6Split[1]);
            Inet6Address startAdress = (Inet6Address)InetAddress.getByName(ip);
            byte[] startBytes = startAdress.getAddress();
            byte[] endBytes = startBytes.clone();
            for (int index = prefixLength / 8; index < 16; index++) {
                endBytes[index] = (byte)0xFF;
            }
            return new IpRange(startAdress.getHostAddress(), InetAddress.getByAddress(endBytes).getHostAddress());
        } catch (Exception e) {
            log.error("get ipv6 segment range error: ", e);
            return new IpRange("0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0");
        }
    }

    private static byte[] toByteArray(int[] addressAsInts) {
        byte[] bytes = new byte[16];
        for (int i = 0; i < addressAsInts.length; i++) {
            ByteBuffer.wrap(bytes, i * 4, 4).putInt(addressAsInts[i]);
        }
        return bytes;
    }

    private static byte[] toByteArray2(int[] addressAsInts) {
        byte[] bytes = new byte[16];
        for (int i = 0; i < addressAsInts.length; i++) {
            bytes[i * 4] = (byte)((addressAsInts[i] >> 24) & 0xff);
            bytes[i * 4 + 1] = (byte)((addressAsInts[i] >> 16) & 0xff);
            bytes[i * 4 + 2] = (byte)((addressAsInts[i] >> 8) & 0xff);
            bytes[i * 4 + 3] = (byte)(addressAsInts[i] & 0xff);
        }
        return bytes;
    }

    @Data
    static class IpRange {
        String start;
        String end;

        IpRange(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }
}
