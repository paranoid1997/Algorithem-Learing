package com.liu.Tree;

public class AVLTree {

    public static class AVLNode<K extends Comparable<K>,V> {
        public K key;
        public V value;
        public int height;
        public AVLNode<K,V> left;
        public AVLNode<K,V> right;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
        }
    }

    /**
     * AVL树的增删改查
     */
    public static class AVLTree1<K extends Comparable<K>,V> {
        private AVLNode<K,V> root;
        private int size;

        public AVLTree1() {
            root = null;
            size = 0;
        }
        /**
         * AVL左旋
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            cur.height = Math.max((cur.left != null ? cur.left.height : 0), (cur.right != null ? cur.right.height : 0)) + 1;
            right.height = Math.max((right.left != null ? right.left.height : 0), (right.right != null ? right.right.height : 0)) + 1;
            return right;
        }
        /**
         * AVL右旋
         */
        private AVLNode<K,V> rightRotate(AVLNode<K,V> cur) {
            AVLNode<K,V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            cur.height = Math.max((cur.left != null ? cur.left.height : 0),(cur.right != null ? cur.right.height : 0)) + 1;
            left.height = Math.max((left.left != null ? left.left.height : 0),(left.right != null ? left.right.height : 0)) + 1;
            return left;
        }

        /**
         * AVL之调整平衡性
         */
        private AVLNode<K,V> maintain(AVLNode<K,V> cur) {
            if (cur == null) return null;
            int leftHeight = cur.left != null ? cur.left.height : 0;
            int rightHeight = cur.right != null ? cur.right.height : 0;
            if (Math.abs(leftHeight - rightHeight) > 1) {
                //平衡因子如果大于1就要进行调整
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                    int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
                    if (leftLeftHeight >= leftRightHeight) {
                        cur = rightRotate(cur);//右旋
                    }else {
                        //左右旋
                        cur.left = leftRotate(cur.left);
                        cur = rightRotate(cur);
                    }
                }else {
                    int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                    int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
                    if (rightRightHeight >= rightLeftHeight) {
                        cur = leftRotate(cur);//左旋
                    }else {
                        cur.right = rightRotate(cur.right);
                        cur = leftRotate(cur);//右左旋
                    }
                }
            }
            return cur;
        }

        /**
         * AVL之增加节点
         */
        private AVLNode<K,V> add(AVLNode<K,V> cur,K key,V value) {
            if (cur == null) {
                return new AVLNode<K,V>(key,value);
            }else {
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left,key,value);
                }else {
                    cur.right = add(cur.right,key,value);
                }
                cur.height = Math.max(cur.left != null ? cur.left.height : 0,cur.right != null ? cur.right.height : 0) + 1;
                return maintain(cur);
            }
        }
        /**
         * AVL之删除节点
         */
        private AVLNode<K,V> delete(AVLNode<K,V> cur,K key) {
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right,key);
            }else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left,key);
            }else {
                if (cur.left == null && cur.right == null) {
                    cur = null;
                }else if (cur.left == null && cur.right != null) {
                    cur = cur.right;
                }else if (cur.left != null && cur.right == null) {
                    cur = cur.left;
                }else {
                    //要删除的节点均有左右孩子
                    //找到右节点上最左的孩子做替换
                    AVLNode<K, V> des = cur.right;
                    while (des.left != null) {
                        des = des.left;
                    }
                    cur.right = delete(cur.right,des.key);//删除右子树上最左的节点
                    des.left = cur.left;
                    des.right = cur.right;
                    cur = des;
                }
            }
            if (cur != null) {
                cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
            }
            return maintain(cur);
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }
        public int size() {
            return size;
        }
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return ans;
        }

        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.value = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.value;
            }
            return null;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.key;
        }

    }
}
