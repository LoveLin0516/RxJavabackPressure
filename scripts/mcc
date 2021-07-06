#!/bin/bash

# Android 直接由开发同学在当前仓库目录调用 ./scripts/mcc init即可，
# 不用传当前仓库目录
command_init="init"

# 给iOS cup使用，需要传入当前仓库目录
# iOS直接在cup 仓库时，调用 ./mcc install "仓库名" 即可
# 当然iOS同学想在当前仓库更新相关配置和脚本文件时，也可以直接在当前仓库目录调用./scripts/mcc init
command_cup_install="install"

command="$1"
parentDir="$2"
commitUrl="https://img2.baidu.com/it/u=2444915445,3839538342&fm=26&fmt=auto&gp=0.jpg"
lccUrl="https://img2.baidu.com/it/u=2444915445,3839538342&fm=26&fmt=auto&gp=0.jpg"
innerDir="scripts"
commitFileName="commit-msg"
lccFileName="lcc"


# Android 在当前仓库 ./script/mcc init 调用方式
function doExecute() {
  # wget有可能未安装，所以这里使用curl  wget -O commit-msg $commitUrl
  curl "$commitUrl" >> "$innerDir/$commitFileName"
  curl "$lccUrl" >> "$innerDir/$lccFileName"

  cp "$innerDir/$commitFileName" ".git/hooks/$commitFileName"
  chmod +x ".git/hooks/$commitFileName"

  chmod +x "$innerDir/$lccFileName"

}


# iOS Cup 使用的方式，即./mcc install "仓库名"
function doExecuteForCup() {
  rm -rf "$parentDir/${innerDir:?}"
  mkdir "$parentDir/$innerDir"
  # wget有可能未安装，所以这里使用curl  wget -O commit-msg $commitUrl
  curl "$commitUrl" >> "$parentDir/$innerDir/$commitFileName"
  curl "$lccUrl" >> "$parentDir/$innerDir/$lccFileName"

  cp "$parentDir/$innerDir/$commitFileName" "$parentDir/.git/hooks/$commitFileName"
  chmod +x "$parentDir/.git/hooks/$commitFileName"

  chmod +x "$parentDir/$innerDir/$lccFileName"
}

echo "$parentDir"

if [[ -z "$command" ]];
then
    echo "指令不能为空..."
    exit 1
fi


if [ "$command" == "$command_init" ];
then
   doExecute

elif [ "$command" == "$command_cup_install" ];
then
    if [[ -z "$parentDir" ]];
    then
        echo "使用iOS cup install指令时，当前仓库名不能为空..."
        exit 1
    fi
    doExecuteForCup

fi





