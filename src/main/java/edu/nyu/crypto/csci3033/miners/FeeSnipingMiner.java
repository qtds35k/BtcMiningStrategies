package edu.nyu.crypto.csci3033.miners;

import edu.nyu.crypto.csci3033.blockchain.Block;
import edu.nyu.crypto.csci3033.blockchain.NetworkStatistics;

public class FeeSnipingMiner extends BaseMiner implements Miner {
	private Block currentHead;
	private double myWeight;
	private double snipingThreashold = 0.85;

	public FeeSnipingMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
	}

	@Override
	public Block currentlyMiningAt() {
		return currentHead;
	}

	@Override
	public Block currentHead() {
		return currentHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
//		 System.out.println("Block: " + block.getBlockValue());
//		 System.out.println("Expected reward = " +block.getBlockValue() * myWeight);
		if (isMinerMe) {
			if (block.getHeight() > currentHead.getHeight()) {
				this.currentHead = block;
			}
		} else {
			if (currentHead == null) {
				currentHead = block;
			} else if (block != null && block.getHeight() > currentHead.getHeight()) {
				if (block.getBlockValue() * myWeight * myWeight < snipingThreashold) {
					// if reward is not enough, act compliant
					this.currentHead = block;
				} else {
					// else, ignore new found block and mine from the previous one (if exists)
					Block prevBlock = block.getPreviousBlock();
					this.currentHead = (prevBlock == null) ? block : prevBlock;
				}
			}
		}
	}

	@Override
	public void initialize(Block genesis, NetworkStatistics networkStatistics) {
		this.currentHead = genesis;
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		myWeight = (double) this.getHashRate() / statistics.getTotalHashRate();
	}
}
