package likou;

/**
 * @author: BYDylan
 * @date: 2024-06-12 20:35:44
 * @description: 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。 数字 1-9 在每一行只能出现一次。 数字 1-9 在每一列只能出现一次。 数字 1-9
 *               在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 */
public class Solution34 {
    public boolean isValidSudoku(char[][] board) {
        // 每一行
        int[][] rows = new int[9][9];
        // 每一列
        int[][] columns = new int[9][9];
        // 每一个小宫格
        int[][][] subboxes = new int[3][3][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                // 不是数字, 则在对应的下标设置值为1, 若那个位置值>1, 说明有重复
                if (c != '.') {
                    int index = c - '0' - 1;
                    rows[i][index]++;
                    columns[j][index]++;
                    subboxes[i / 3][j / 3][index]++;
                    if (rows[i][index] > 1 || columns[j][index] > 1 || subboxes[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
