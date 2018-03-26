package com.ican;

import java.util.HashMap;
import java.util.List;


class Node{
    public Node parentNode;
    public String name;
    public List<Node> childNodes;
}

public class test {

    public static void main(String[] args) {
        int[] man1 = {1000,1001,1002,1003,1004};
        int[] man2 = {1005,1006,1007,1008,1009};
        int[] man3 = {1010,1011,1012,1013,1014};
        int[] man4 = {1015,1016,1017,1018,1019};

        HashMap map = new HashMap<>();


    }

    /*public static void main(String[] args) {
        String[] names = {"核心思想", "字典树", "哈希树", "核弹头", "编辑器"};
        Node root = new Node();

        for (int i=0; i<names.length; i++) {
            for (int j=0; j<names[i].length()-1; j++){

                String ch = names[i].substring(j, j + 1);
                if (root.childNodes == null) {
                    Node node = new Node();
                    node.parentNode = root;
                    node.name = ch;
                    root.childNodes.add(node);
                } else {

                }

            }
        }
    }*/
}
