package com.ican;

import java.util.List;

class Node{
    public Node parentNode;
    public String name;
    public List<Node> childNodes;
}

public class test {

    public static void main(String[] args) {
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

    }
}
