package com.supbank.util;

import com.supbank.dto.Block;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author: zhiming
 * @create: 2019/05/09 15:13
 **/
@Data
public class DigMine {
  private int  difficulty;

  public DigMine(int difficulty){
    this.difficulty=difficulty;
  }

  /**
   * 挖矿
   *
   * @param uid 用户的id
   */
  public static Block digOneBlock(String uid, Block oldBlock,int vac,int difficulty) {
    //  用用户的id去数据库中选择


    //下面是生成一个新的区块
    Block newBlock = new Block();

    newBlock.setIndex(oldBlock.getIndex() + 1);
    newBlock.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    newBlock.setVac(vac);
    newBlock.setPrevHash(oldBlock.getHash());
    newBlock.setDifficulty(difficulty);

    /*
     * 这里的 for 循环很重要： 获得 i 的十六进制表示 ，将 Nonce 设置为这个值，并传入 calculateHash 计算哈希值。
     * 之后通过上面的 isHashValid 函数判断是否满足难度要求，如果不满足就重复尝试。 这个计算过程会一直持续，直到求得了满足要求的
     * Nonce 值，之后通过 handleWriteBlock 函数将新块加入到链上。
     */
    for (int i = 0; ; i++) {
      String hex = String.format("%x", i);
      newBlock.setNonce(hex);
      if (!isHashValid(BlockUtils.calculateHash(newBlock), newBlock.getDifficulty())) {
        System.out.printf("%s do more work!\n", BlockUtils.calculateHash(newBlock));
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        continue;
      } else {
        System.out.printf("%s work done!\n", BlockUtils.calculateHash(newBlock));
        newBlock.setHash(BlockUtils.calculateHash(newBlock));
        break;
      }
    }
    return newBlock;
  }

  /**
   * 校验HASH的合法性
   */
  public static boolean isHashValid(String hash, int difficulty) {
    String prefix = repeat("0", difficulty);
    return hash.startsWith(prefix);
  }

  public static String repeat(String str, int repeat) {
    final StringBuilder buf = new StringBuilder();
    for (int i = 0; i < repeat; i++) {
      buf.append(str);
    }
    return buf.toString();
  }
}
