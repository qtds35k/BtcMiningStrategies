package edu.nyu.crypto.csci3033.miners;

import edu.nyu.crypto.csci3033.blockchain.Block;
import edu.nyu.crypto.csci3033.blockchain.NetworkStatistics;

public class SelfishMiner extends BaseMiner implements Miner {
	private Block currentHead;
	private Block selfishHead;
	boolean selfishFlag = false;
	private double myWeight;
	private boolean attackOn = false;

	public SelfishMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
	}

	@Override
	public Block currentlyMiningAt() {
		return selfishHead;
	}

	@Override
	public Block currentHead() {
		return currentHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
		if (isMinerMe) {
			if (block.getHeight() > selfishHead.getHeight()) {
					this.selfishHead = block;
					selfishFlag = true;
			}
		} else {
			if (selfishHead == null) {
				selfishHead = block;
				currentHead = block;
			} else if (block != null) {
				int aheadBlocks = selfishHead.getHeight() - block.getHeight();
				if (selfishFlag) {
					if (aheadBlocks > 1) {
						
					}
					else if (aheadBlocks == 1 || aheadBlocks == 0) {
						this.currentHead = selfishHead;
						selfishFlag = false;
					} else {
						this.selfishHead = block;
						this.currentHead = block;
						selfishFlag = false;
					}
				} else if (block != null && block.getHeight() > selfishHead.getHeight()) {
					this.selfishHead = block;
					this.currentHead = block;
				}
			}
		}
	}

	@Override
	public void initialize(Block genesis, NetworkStatistics networkStatistics) {
		this.currentHead = genesis;
		this.selfishHead = genesis;
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		// If you assume that you only have a 50 percent chance of winning these races
		// selfish mining is an improvement over the default strategy if a > 25%
		// Even if you lose every race, selfish mining is still more profitable if a >
		// 33.3%
		myWeight = (double) this.getHashRate() / statistics.getTotalHashRate();
		if (myWeight > 0.333)
			this.attackOn = true;
		else
			this.attackOn = false;
	}
}
