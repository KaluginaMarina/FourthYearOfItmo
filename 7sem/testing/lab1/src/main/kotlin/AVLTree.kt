/**
 * from https://gist.github.com/nehaljwani/8243688
 */

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class AVLTree {
    inner class Node(var value: Int) {
        var left: Node? = null
        var right: Node? = null
        val parent: Node? = null
        var height = 1

    }

    fun height(N: Node?): Int {
        return N?.height ?: 0
    }

    fun insert(node: Node?, value: Int): Node? {
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            return Node(value)
        }
        if (value < node.value) node.left = insert(node.left, value) else node.right = insert(node.right, value)

        /* 2. Update height of this ancestor node */
        node.height =
            Math.max(height(node.left), height(node.right)) + 1

        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
        val balance = getBalance(node)

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && value < node.left!!.value) return rightRotate(node)

        // Right Right Case
        if (balance < -1 && value > node.right!!.value) return leftRotate(node)

        // Left Right Case
        if (balance > 1 && value > node.left!!.value) {
            node.left = leftRotate(node.left)
            return rightRotate(node)
        }

        // Right Left Case
        if (balance < -1 && value < node.right!!.value) {
            node.right = rightRotate(node.right)
            return leftRotate(node)
        }

        /* return the (unchanged) node pointer */return node
    }

    private fun rightRotate(y: Node?): Node? {
        val x = y!!.left
        val T2 = x!!.right

        // Perform rotation
        x.right = y
        y.left = T2

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1
        x.height = Math.max(height(x.left), height(x.right)) + 1

        // Return new root
        return x
    }

    private fun leftRotate(x: Node?): Node? {
        val y = x!!.right
        val T2 = y!!.left

        // Perform rotation
        y.left = x
        x.right = T2

        //  Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1
        y.height = Math.max(height(y.left), height(y.right)) + 1

        // Return new root
        return y
    }

    // Get Balance factor of node N
    private fun getBalance(N: Node?): Int {
        return if (N == null) 0 else height(N.left) - height(N.right)
    }

    fun preOrder(root: Node?, res: java.lang.StringBuilder = java.lang.StringBuilder()) : String {
        if (root != null) {
            preOrder(root.left, res)
            res.append(root.value).append(" ")
            preOrder(root.right, res)
        }
        return res.toString()
    }

    private fun minValueNode(node: Node?): Node? {
        var current = node
        /* loop down to find the leftmost leaf */while (current!!.left != null) current = current.left
        return current
    }

    fun deleteNode(root: Node?, value: Int): Node? {
        // STEP 1: PERFORM STANDARD BST DELETE
        var root = root
        if (root == null) return root

        // If the value to be deleted is smaller than the root's value,
        // then it lies in left subtree
        if (value < root.value) root.left = deleteNode(root.left, value) else if (value > root.value) root.right =
            deleteNode(root.right, value) else {
            // node with only one child or no child
            if (root.left == null || root.right == null) {
                var temp: Node?
                temp = if (root.left != null) root.left else root.right

                // No child case
                if (temp == null) {
                    temp = root
                    root = null
                } else  // One child case
                    root = temp // Copy the contents of the non-empty child
                temp = null
            } else {
                // node with two children: Get the inorder successor (smallest
                // in the right subtree)
                val temp = minValueNode(root.right)

                // Copy the inorder successor's data to this node
                root.value = temp!!.value

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.value)
            }
        }

        // If the tree had only one node then return
        if (root == null) return root

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        val balance = getBalance(root)

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) return rightRotate(root)

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left)
            return rightRotate(root)
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) return leftRotate(root)

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right)
            return leftRotate(root)
        }
        return root
    }

    fun print(root: Node?) {
        if (root == null) {
            println("(XXXXXX)")
            return
        }
        val height = root.height
        val width = Math.pow(2.0, height - 1.toDouble()).toInt()

        // Preparing variables for loop.
        var current: MutableList<Node?> = ArrayList(1)
        var next: MutableList<Node?> = ArrayList(2)
        current.add(root)
        val maxHalfLength = 4
        var elements = 1
        val sb = StringBuilder(maxHalfLength * width)
        for (i in 0 until maxHalfLength * width) sb.append(' ')
        var textBuffer: String

        // Iterating through height levels.
        for (i in 0 until height) {
            sb.setLength(maxHalfLength * (Math.pow(2.0, height - 1 - i.toDouble()).toInt() - 1))

            // Creating spacer space indicator.
            textBuffer = sb.toString()

            // Print tree node elements
            for (n in current) {
                print(textBuffer)
                if (n == null) {
                    print("        ")
                    next.add(null)
                    next.add(null)
                } else {
                    System.out.printf("(%6d)", n.value)
                    next.add(n.left)
                    next.add(n.right)
                }
                print(textBuffer)
            }
            println()
            // Print tree node extensions for next level.
            if (i < height - 1) {
                for (n in current) {
                    print(textBuffer)
                    if (n == null) print("        ") else System.out.printf(
                        "%s      %s",
                        if (n.left == null) " " else "/", if (n.right == null) " " else "\\"
                    )
                    print(textBuffer)
                }
                println()
            }

            // Renewing indicators for next run.
            elements *= 2
            current = next
            next = ArrayList(elements)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val t = AVLTree()
            var root: Node? = null
            while (true) {
                println("(1) Insert")
                println("(2) Delete")
                try {
                    val bufferRead = BufferedReader(InputStreamReader(System.`in`))
                    val s = bufferRead.readLine()
                    root = if (s.toInt() == 1) {
                        print("Value to be inserted: ")
                        t.insert(root, bufferRead.readLine().toInt())
                    } else if (s.toInt() == 2) {
                        print("Value to be deleted: ")
                        t.deleteNode(root, bufferRead.readLine().toInt())
                    } else {
                        println("Invalid choice, try again!")
                        continue
                    }
                    t.print(root)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                println(t.preOrder(root))
            }
        }
    }
}