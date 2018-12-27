package edu.nyu.crypto.csci3033.miners;

import edu.nyu.crypto.csci3033.blockchain.Block;
import edu.nyu.crypto.csci3033.blockchain.NetworkStatistics;

public class MajorityMiner extends BaseMiner implements Miner {
	private Block currentHead;
	private double myWeight;
	private boolean loseMajority = false;

	public MajorityMiner(String id, int hashRate, int connectivity) {
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
		if (isMinerMe) {
			if (block.getHeight() > currentHead.getHeight()) {
				this.currentHead = block;
			}
		} else {
			if (currentHead == null && block.getHeight() > 5) {
				currentHead = block;
			} else if (block != null && block.getHeight() > currentHead.getHeight() + 5 && loseMajority) {
				this.currentHead = block;
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
		if (myWeight < 0.51)
			this.loseMajority = true;
		else
			this.loseMajority = false;
	}
}
