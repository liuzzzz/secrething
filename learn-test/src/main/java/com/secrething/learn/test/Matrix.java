package com.secrething.learn.test;

/**
 * Created by liuzz on 2019-05-08 21:38.
 */
public class Matrix {

    public static void main(String[] args) {
        accessRoomByMark(1,2,5,'N','L','G','L','G','L','G','L','G','G');
        accessMatrix(buildMatrix(5));


    }

    //思路  没用到
    private static int[] getByMark(String mark,int matrixLen){
        String[] arr = mark.split(",");
        int x = Integer.valueOf(arr[0]);
        int y = Integer.valueOf(arr[1]);
        int[] res = new int[2];
        res[0] = matrixLen - y -1;
        res[1] = x;
        return res;
    }

    //测试代码
    private static String[][] buildMatrix(int n){
        String[][] matrix = new String[n][n];
        for (int i = n-1,idx=0; i >= 0; --i,idx++) {
            for (int j = 0; j < n; j++) {
                String mark = j+","+idx;
                matrix[i][j] = mark;
            }
        }
        return matrix;
    }
    //测试代码
    private static void accessMatrix(String[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void accessRoomByMark(int x,int y,int max,char initDirection,char... commands){
        Robot robot = new Robot(max,x,y,initDirection);
        for (int i = 0; i < commands.length; i++) {
            robot.execCommand(commands[i]);
        }
        System.out.println(robot);

    }


    private static class Robot {
        int max;
        int x;
        int y;
        char direction;

        public Robot(int max, int x, int y, char direction) {
            this.max = max -1;
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public Robot execCommand(char command){
            switch (direction){
                case 'N':{
                    switch (command){
                        case 'L':{
                            this.direction = 'W';
                            break;
                        }
                        case 'R':{
                            this.direction = 'E';
                            break;

                        }
                        case 'G':{
                            this.y = this.y + 1;
                            break;

                        }
                        default:
                            throw new UnsupportedOperationException("unknown command ");
                    }
                    break;
                }
                case 'S':{
                    switch (command){
                        case 'L':{
                            this.direction = 'E';
                            break;
                        }
                        case 'R':{
                            this.direction = 'W';
                            break;

                        }
                        case 'G':{
                            this.y = this.y - 1;
                            break;

                        }
                        default:
                            throw new UnsupportedOperationException("unknown command ");
                    }
                    break;
                }
                case 'E':{
                    switch (command){
                        case 'L':{
                            this.direction = 'N';
                            break;
                        }
                        case 'R':{
                            this.direction = 'S';
                            break;

                        }
                        case 'G':{
                            this.x = this.x + 1;
                            break;

                        }
                        default:
                            throw new UnsupportedOperationException("unknown command ");
                    }
                    break;
                }
                case 'W':{
                    switch (command){
                        case 'L':{
                            this.direction = 'S';
                            break;
                        }
                        case 'R':{
                            this.direction = 'N';
                            break;

                        }
                        case 'G':{
                            this.x = this.x - 1;
                            break;

                        }
                        default:
                            throw new UnsupportedOperationException("unknown command ");
                    }
                    break;
                }
                default:{
                    throw new UnsupportedOperationException("unknown direction ");
                }
            }
            if (x < 0 || y < 0 || x >= max || y >= max){
                throw new UnsupportedOperationException("out of bounds");
            }
            return this;
        }

        @Override
        public String toString() {
            return "Robot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", direction=" + direction +
                    '}';
        }
    }

}
