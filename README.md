# Threaded Binary Search Tree Project

## Overview
This project implements a **Threaded Binary Search Tree (BST)** in Java.  
It extends a regular BST by adding **thread pointers** to allow efficient in-order traversal without using a stack or recursion.

## Files Included
- `BinaryNode.java`  
  Defines the node structure with:
  - data
  - left & right children
  - parent reference
  - thread reference

- `BinaryTree.java`  
  Implements a general binary tree with:
  - tree construction
  - threaded inorder iterator

- `BinarySearchTree.java`  
  Extends `BinaryTree` and adds:
  - insertion (`add`)
  - removal (`remove`)
  - search (`contains`, `getEntry`)
  - thread maintenance

- `SearchTreeInterface.java`  
  Interface defining BST operations.

- `TreeInterface.java`  
  Basic tree operations (height, size, etc.)

- `TreeIteratorInterface.java`  
  Iterator methods for tree traversal.

- `Identifiers.java`  
  Client program that:
  - reads a file
  - extracts tokens (identifiers)
  - stores them in a BST
  - prints them in sorted order using threads

## How It Works
- Each node may contain a **thread pointer** to its inorder successor.
- This allows traversal without recursion or a stack.
- When inserting or removing nodes, threads are updated accordingly.

## How to Run
1. Compile all files:
